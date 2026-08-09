package ru.moxutos.meteo.weather.model.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record WeatherRecord(
        Long id,
        Long cityId,
        Long sourceId,
        OffsetDateTime measuredAt,
        BigDecimal temperature,
        BigDecimal feelsLike,
        BigDecimal humidity,
        BigDecimal pressure,
        BigDecimal windSpeed,
        Integer windDirection,
        Integer weatherCode
) {
}
