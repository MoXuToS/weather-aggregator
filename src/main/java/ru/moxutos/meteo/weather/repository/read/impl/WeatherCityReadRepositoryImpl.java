package ru.moxutos.meteo.weather.repository.read.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherCityJpaEntity;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherCityJpaEntity_;
import ru.moxutos.meteo.weather.repository.read.WeatherCityReadRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WeatherCityReadRepositoryImpl implements WeatherCityReadRepository {

    @PersistenceContext
    private final EntityManager jpaEntityManager;

    @Override
    public List<WeatherCityJpaEntity> getAllCityForCollectingWeather(Pageable pageable) {
        CriteriaBuilder cb = jpaEntityManager.getCriteriaBuilder();

        CriteriaQuery<WeatherCityJpaEntity> query =
                cb.createQuery(WeatherCityJpaEntity.class);

        Root<WeatherCityJpaEntity> root =
                query.from(WeatherCityJpaEntity.class);

        query.select(root)
                .where(cb.isTrue(root.get(WeatherCityJpaEntity_.COLLECT_WEATHER)))
                .orderBy(cb.asc(root.get(WeatherCityJpaEntity_.CITY_NAME)));

        return jpaEntityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
