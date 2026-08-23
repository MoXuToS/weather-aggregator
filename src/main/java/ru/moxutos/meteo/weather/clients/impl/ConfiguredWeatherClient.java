package ru.moxutos.meteo.weather.clients.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.moxutos.meteo.weather.clients.WeatherClient;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherCityJpaEntity;
import ru.moxutos.meteo.weather.model.record.WeatherSource;

@RequiredArgsConstructor
public class ConfiguredWeatherClient implements WeatherClient {

    private final WeatherSource source;
    private final WebClient webClient;

    @Override
    public String sourceName() {
        return source.name();
    }

    @Override
    public Mono<Object> getWeather(WeatherCityJpaEntity city) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder
                            .queryParam("latitude", city.getLatitude())
                            .queryParam("longitude", city.getLongitude());
                    source.requestParams().forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(Object.class);
    }
}
