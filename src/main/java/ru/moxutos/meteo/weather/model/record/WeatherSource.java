package ru.moxutos.meteo.weather.model.record;

public record WeatherSource(
        Long id,
        String name,
        String baseUrl,
        Boolean enabled,
        String apiKey
) {
}
