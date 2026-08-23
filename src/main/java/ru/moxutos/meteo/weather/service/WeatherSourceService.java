package ru.moxutos.meteo.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.moxutos.meteo.weather.mapper.WeatherSourceMapper;
import ru.moxutos.meteo.weather.model.domain.r2dbc.WeatherSourceR2dbcEntity;
import ru.moxutos.meteo.weather.model.record.WeatherSource;
import ru.moxutos.meteo.weather.repository.read.WeatherSourceReadRepository;
import ru.moxutos.meteo.weather.repository.write.WeatherSourceWriteRepository;

import java.util.Map;

/**
 * Сервис по работе с источниками погоды.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherSourceService {

    private final WeatherSourceReadRepository weatherSourceReadRepository;
    private final WeatherSourceWriteRepository weatherSourceWriteRepository;
    private final WeatherSourceMapper weatherSourceMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TransactionalOperator transactionalOperator;

    /**
     * Добавить источник погоды.
     *
     * @param weatherSource источник погоды.
     * @return пустой ответ если всё успешно.
     */
    public Mono<Void> createWeatherSource(WeatherSource weatherSource) {
        return Mono.fromCallable(() -> weatherSourceReadRepository.checkExistsByName(weatherSource.name()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(
                                new IllegalArgumentException("Данный источник погоды существует: " + weatherSource.name())
                        );
                    }
                    log.info("Источник погоды: {} не существует добавялем.", weatherSource.name());
                    return saveWeatherSource(weatherSource);
                });
    }

    private Mono<Void> saveWeatherSource(WeatherSource weatherSource) {
        WeatherSourceR2dbcEntity weatherSourceR2dbcEntity = weatherSourceMapper.toDomain(weatherSource);
        return weatherSourceWriteRepository.save(weatherSourceR2dbcEntity).then();
    }

    /**
     *
     * @param weatherSource источник погоды
     * @return измененный источник погоды
     */
    public Mono<WeatherSource> updateWeatherByName(WeatherSource weatherSource) {
        log.info("Обновляем источник погоды по наименованию: {}", weatherSource.name());
        return transactionalOperator.transactional(updateWeather(weatherSource));
    }

    private Mono<WeatherSource> updateWeather(WeatherSource weatherSource) {
        Json requestParams = getRequestParamsAsJson(weatherSource.requestParams());
        return weatherSourceWriteRepository.updateWeatherByName(weatherSource.name(), weatherSource.baseUrl(),
                        weatherSource.enabled(), weatherSource.apiKey(), requestParams)
                .flatMap(updatedRows -> {
                    if (updatedRows == 0) {
                        return Mono.error(
                                new IllegalArgumentException("Данный источник погоды не существует: " + weatherSource.name())
                        );
                    }
                    return Mono.just(weatherSource);
                });
    }

    private Json getRequestParamsAsJson(Map<String, Object> requestParams) {
        try {
            return Json.of(objectMapper.writeValueAsString(requestParams));
        } catch (JsonProcessingException e) {
            log.warn("Ошибка при преобразование requestParams to JSON: {}", requestParams, e);
            throw new IllegalArgumentException("Некорректный requestParams в JSON");
        }

    }
}
