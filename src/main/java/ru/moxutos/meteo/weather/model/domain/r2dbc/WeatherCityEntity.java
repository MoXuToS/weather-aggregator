package ru.moxutos.meteo.weather.model.domain.r2dbc;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "weather_city")
public class WeatherCityEntity {

    @Id
    private Long id;

    @Size(max = 128)
    @NotNull
    @Column("city_name")
    private String cityName;

    @NotNull
    @Column("latitude")
    private BigDecimal latitude;

    @NotNull
    @Column("longitude")
    private BigDecimal longitude;

    @Column("collect_weather")
    private Boolean collectWeather;

}