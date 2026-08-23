package ru.moxutos.meteo.weather.model.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "weather_source")
public class WeatherSourceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id", nullable = false)
    private Long id;

    @Size(max = 64)
    @NotNull
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Size(max = 256)
    @NotNull
    @Column(name = "base_url", nullable = false, length = 256)
    private String baseUrl;

    @ColumnDefault("true")
    @Column(name = "enabled")
    private Boolean enabled;

    @Size(max = 256)
    @Column(name = "api_key", length = 256)
    private String apiKey;

    @OneToMany(mappedBy = "source")
    private Set<WeatherRecordJpaEntity> weatherRecords = new LinkedHashSet<>();

    @Column(name = "request_params")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> requestParams;

}