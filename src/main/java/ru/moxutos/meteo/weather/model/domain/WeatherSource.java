package ru.moxutos.meteo.weather.model.domain;

public record WeatherSource(
        Long id,
        String name,
        String baseUrl,
        Boolean enabled,
        String apiKey
) {
}
