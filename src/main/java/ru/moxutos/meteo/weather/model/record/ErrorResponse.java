package ru.moxutos.meteo.weather.model.record;

public record ErrorResponse(
        String code,
        String message
) {
}
