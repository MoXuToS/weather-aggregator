package ru.moxutos.meteo.weather.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.moxutos.meteo.weather.model.domain.jpa.WeatherSourceJpaEntity;
import ru.moxutos.meteo.weather.model.domain.r2dbc.WeatherSourceR2dbcEntity;
import ru.moxutos.meteo.weather.model.record.WeatherSource;

import java.util.Map;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WeatherSourceMapper {

    @Mapping(target = "requestParams", source = "requestParams", qualifiedByName = "mapRequestParams")
    WeatherSourceR2dbcEntity toDomain(WeatherSource entity);

    WeatherSource toRecord(WeatherSourceJpaEntity source);

    @SneakyThrows
    @Named("mapRequestParams")
    default Json getRequestParamsAsJson(Map<String, Object> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return Json.of(objectMapper.writeValueAsString(requestParams));
    }

}
