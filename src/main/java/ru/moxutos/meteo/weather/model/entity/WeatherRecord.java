package ru.moxutos.meteo.weather.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "weather_record", indexes = {
        @Index(name = "uk_weather_record_city_source_measured_at", columnList = "city_id, source_id, measured_at",
                unique = true),
        @Index(name = "idx_weather_record_city_measured_at", columnList = "city_id, measured_at"),
        @Index(name = "idx_weather_record_source_measured_at", columnList = "source_id, measured_at")
})
public class WeatherRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private WeatherCity city;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private WeatherSource source;

    @NotNull
    @Column(name = "measured_at", nullable = false)
    private OffsetDateTime measuredAt;

    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(name = "feels_like", precision = 5, scale = 2)
    private BigDecimal feelsLike;

    @Column(name = "humidity", precision = 5, scale = 2)
    private BigDecimal humidity;

    @Column(name = "pressure", precision = 7, scale = 2)
    private BigDecimal pressure;

    @Column(name = "wind_speed", precision = 6, scale = 2)
    private BigDecimal windSpeed;

    @Column(name = "wind_direction", precision = 6, scale = 2)
    private BigDecimal windDirection;

    @NotNull
    @Column(name = "weather_code", nullable = false)
    private Integer weatherCode;

}