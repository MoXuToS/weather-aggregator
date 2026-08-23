package ru.moxutos.meteo.weather.model.record;

import java.util.Map;

public record WeatherSource(
        String name,
        String baseUrl,
        Boolean enabled,
        String apiKey,
        Map<String, Object> requestParams)
{ }
