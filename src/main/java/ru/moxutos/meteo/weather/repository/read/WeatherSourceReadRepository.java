package ru.moxutos.meteo.weather.repository.read;

import ru.moxutos.meteo.weather.model.domain.jpa.WeatherSourceJpaEntity;

import java.util.List;

public interface WeatherSourceReadRepository {

    /**
     * Проверить наличие источника данных по наименованию.
     * @param name наименование источника
     * @return есть ли источник
     */
    boolean checkExistsByName(String name);

    List<WeatherSourceJpaEntity> findAllEnabled();
}
