package ru.moxutos.meteo.weather.model.record;

import java.math.BigDecimal;

public record WeatherCity(
        Long id,
        String cityName,
        BigDecimal latitude,
        BigDecimal longitude,
        Boolean collectWeather
) {
}