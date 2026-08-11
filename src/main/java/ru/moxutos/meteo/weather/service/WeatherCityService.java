package ru.moxutos.meteo.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.moxutos.meteo.weather.mapper.WeatherCityMapper;
import ru.moxutos.meteo.weather.model.record.PagingRequest;
import ru.moxutos.meteo.weather.model.record.WeatherCity;
import ru.moxutos.meteo.weather.repository.read.WeatherCityReadRepository;
import ru.moxutos.meteo.weather.utils.PageableUtils;

/**
 * Сервис по предоставлению информации о городах.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherCityService {

    private final WeatherCityReadRepository weatherCityReadRepository;
    private final WeatherCityMapper weatherCityMapper;

    public Flux<WeatherCity> getAllCityForCollectingWeather(PagingRequest pageRequest) {
        Pageable pageable = PageableUtils.convert(pageRequest);
        return Flux.fromIterable(weatherCityReadRepository.getAllCityForCollectingWeather(pageable))
                .map(weatherCityMapper::toDomain);
    }
}
