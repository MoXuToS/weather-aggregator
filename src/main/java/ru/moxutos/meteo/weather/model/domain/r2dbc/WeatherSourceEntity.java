package ru.moxutos.meteo.weather.model.domain.r2dbc;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("weather_source")
public class WeatherSourceEntity {
    
    @Id
    private Long id;

    @Size(max = 64)
    @Column("name")
    private String name;

    @Size(max = 256)
    @NotNull
    @Column("base_url")
    private String baseUrl;

    @ColumnDefault("true")
    @Column("enabled")
    private Boolean enabled;

    @Size(max = 256)
    @Column("api_key")
    private String apiKey;

}