package ru.moxutos.meteo.weather.model.domain.r2dbc;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Table(name = "weather_record")
public class WeatherRecordEntity {

    @Id
    @Column("record_id")
    private Long id;

    @Column("measured_at")
    private OffsetDateTime measuredAt;

    @Column("temperature")
    private BigDecimal temperature;

    @Column("feels_like")
    private BigDecimal feelsLike;

    @Column("humidity")
    private BigDecimal humidity;

    @Column("pressure")
    private BigDecimal pressure;

    @Column("wind_speed")
    private BigDecimal windSpeed;

    @Column("wind_direction")
    private BigDecimal windDirection;

    @NotNull
    @Column("weather_code")
    private Integer weatherCode;

}