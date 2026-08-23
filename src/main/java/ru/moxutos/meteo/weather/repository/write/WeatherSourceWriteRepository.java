package ru.moxutos.meteo.weather.repository.write;

import io.r2dbc.postgresql.codec.Json;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.moxutos.meteo.weather.model.domain.r2dbc.WeatherSourceR2dbcEntity;

@Repository
public interface WeatherSourceWriteRepository extends R2dbcRepository<WeatherSourceR2dbcEntity, Long> {

    @Modifying
    @Query("""
    UPDATE weather_source
    SET base_url = :baseUrl,
        enabled = :enabled,
        api_key = :apiKey,
        request_params = :requestParams
    WHERE name = :name
    """)
    Mono<Integer> updateWeatherByName(String name, String baseUrl, boolean enabled,
                                      String apiKey, Json requestParams);
}
