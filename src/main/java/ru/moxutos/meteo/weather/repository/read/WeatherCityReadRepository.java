package ru.moxutos.meteo.weather.repository.read;

import org.springframework.data.domain.Pageable;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherCityJpaEntity;

import java.util.List;

public interface WeatherCityReadRepository {

    List<WeatherCityJpaEntity> getAllCityForCollectingWeather(Pageable pageable);
}
