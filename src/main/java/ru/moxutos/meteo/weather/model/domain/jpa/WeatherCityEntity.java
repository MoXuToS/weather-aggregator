package ru.moxutos.meteo.weather.model.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "weather_city")
public class WeatherCityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_city_id_gen")
    @SequenceGenerator(name = "weather_city_id_gen", sequenceName = "weather_city_city_id_seq", allocationSize = 1)
    @Column(name = "city_id", nullable = false)
    private Long id;

    @Size(max = 128)
    @NotNull
    @Column(name = "city_name", nullable = false, length = 128)
    private String cityName;

    @NotNull
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @NotNull
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @ColumnDefault("true")
    @Column(name = "collect_weather")
    private Boolean collectWeather;

    @OneToMany(mappedBy = "city")
    private Set<WeatherRecordEntity> weatherRecords = new LinkedHashSet<>();

}