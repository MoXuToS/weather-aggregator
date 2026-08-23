package ru.moxutos.meteo.weather.clients;

import reactor.core.publisher.Mono;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherCityJpaEntity;

public interface WeatherClient {

    String sourceName();

    Mono<Object> getWeather(WeatherCityJpaEntity city);
}
