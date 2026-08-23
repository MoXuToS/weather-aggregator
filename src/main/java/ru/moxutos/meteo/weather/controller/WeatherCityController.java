package ru.moxutos.meteo.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.moxutos.meteo.weather.model.record.PagingRequest;
import ru.moxutos.meteo.weather.model.record.WeatherCity;
import ru.moxutos.meteo.weather.service.WeatherCityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather-city")
@Tag(name = "Weather City", description = "Операции с городами откуда предоставляется погода")
public class WeatherCityController {

    private final WeatherCityService weatherCityService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("get-all-city-for-collecting-weather")
    @Operation(
            summary = "Метод получения списка городов для которых осуществялется поиск погоды",
            description = "Метод получения списка городов для которых осуществялется поиск погоды"
    )
    public Flux<WeatherCity> getAllCityForCollectingWeather(@ParameterObject PagingRequest pageRequest) {
        return weatherCityService.getAllCityForCollectingWeather(pageRequest);
    }

}
