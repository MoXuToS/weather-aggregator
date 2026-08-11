package ru.moxutos.meteo.weather.repository.read;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherSourceJpaEntity;

public interface WeatherSourceReadRepository extends JpaRepository<WeatherSourceJpaEntity, Long> {
}
