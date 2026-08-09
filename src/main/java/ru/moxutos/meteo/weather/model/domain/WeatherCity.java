package ru.moxutos.meteo.weather.model.domain;

import java.math.BigDecimal;

public record WeatherCity(
        Long id,
        String cityName,
        BigDecimal latitude,
        BigDecimal longitude,
        Boolean collectWeather
) {
}