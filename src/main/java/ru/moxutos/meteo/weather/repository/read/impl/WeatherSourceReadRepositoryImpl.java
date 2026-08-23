package ru.moxutos.meteo.weather.repository.read.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherSourceJpaEntity;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherSourceJpaEntity_;
import ru.moxutos.meteo.weather.repository.read.WeatherSourceReadRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WeatherSourceReadRepositoryImpl implements WeatherSourceReadRepository {

    private static final Integer MIN_SCORE = 1;

    @PersistenceContext
    private final EntityManager jpaEntityManager;

    @Override
    public boolean checkExistsByName(String name) {
        CriteriaBuilder cb = jpaEntityManager.getCriteriaBuilder();

        CriteriaQuery<Boolean> query = cb.createQuery(Boolean.class);

        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<WeatherSourceJpaEntity> root = subquery.from(WeatherSourceJpaEntity.class);

        subquery.select(cb.literal(MIN_SCORE))
                .where(cb.equal(root.get(WeatherSourceJpaEntity_.NAME), name));

        query.select(cb.exists(subquery));

        return jpaEntityManager
                .createQuery(query)
                .getSingleResult();
    }

    @Override
    public List<WeatherSourceJpaEntity> findAllEnabled() {

        CriteriaBuilder cb = jpaEntityManager.getCriteriaBuilder();

        CriteriaQuery<WeatherSourceJpaEntity> query = cb.createQuery(WeatherSourceJpaEntity.class);

        Root<WeatherSourceJpaEntity> root = query.from(WeatherSourceJpaEntity.class);

        query.select(root).where(cb.isTrue(root.get(WeatherSourceJpaEntity_.ENABLED)));

        return jpaEntityManager
                .createQuery(query)
                .getResultList();
    }
}
