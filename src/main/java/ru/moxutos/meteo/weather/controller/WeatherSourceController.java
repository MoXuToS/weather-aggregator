package ru.moxutos.meteo.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.moxutos.meteo.weather.model.record.WeatherSource;
import ru.moxutos.meteo.weather.service.WeatherSourceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather-source")
@Tag(name = "Weather source", description = "Операции с источниками данных")
public class WeatherSourceController {

    private final WeatherSourceService weatherSourceService;

    /**
     * Добавить источник погоды.
     * @param weatherSource источник погоды.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    @Operation(
            summary = "Метод по добавлению источника данных о погоде",
            description = "Метод по добавлению источника данных о погоде"
    )
    public Mono<Void> addWeatherSource(@RequestBody WeatherSource weatherSource) {
        return weatherSourceService.createWeatherSource(weatherSource);
    }

    /**
     * Изменить источник погоды по наименованию источника.
     * @param weatherSource источник погоды.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    @Operation(
            summary = "Метод по обновлению источника данных о погоде",
            description = "Метод по обновлению источника данных о погоде"
    )
    public Mono<WeatherSource> updateWeather(@RequestBody WeatherSource weatherSource) {
        return weatherSourceService.updateWeatherByName(weatherSource);
    }
}
