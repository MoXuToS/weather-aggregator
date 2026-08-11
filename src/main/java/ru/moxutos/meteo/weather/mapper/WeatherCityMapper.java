package ru.moxutos.meteo.weather.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherCityJpaEntity;
import ru.moxutos.meteo.weather.model.record.WeatherCity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WeatherCityMapper {

    WeatherCity toDomain(WeatherCityJpaEntity entity);

}
