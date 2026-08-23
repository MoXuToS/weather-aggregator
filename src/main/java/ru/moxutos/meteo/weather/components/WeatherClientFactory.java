package ru.moxutos.meteo.weather.components;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.moxutos.meteo.weather.clients.WeatherClient;
import ru.moxutos.meteo.weather.clients.impl.ConfiguredWeatherClient;
import ru.moxutos.meteo.weather.model.record.WeatherSource;

@Component
public class WeatherClientFactory {

    public WeatherClient create(WeatherSource source) {
        WebClient webClient = WebClient.builder()
                .baseUrl(source.baseUrl())
                .build();

        return new ConfiguredWeatherClient(source, webClient);
    }
}
