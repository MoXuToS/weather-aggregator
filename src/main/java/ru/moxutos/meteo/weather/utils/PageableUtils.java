package ru.moxutos.meteo.weather.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.moxutos.meteo.weather.model.record.PagingRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class PageableUtils {

    private static final String DEFAULT_DELIMETER = ",";

    public Pageable convert(PagingRequest request) {
        List<Sort.Order> orders = Optional.ofNullable(request.sort())
                .map(sort -> sort.stream()
                        .map(PageableUtils::toOrder)
                        .toList())
                .orElse(Collections.emptyList());
        return PageRequest.of(request.page(), request.size(), Sort.by(orders));
    }

    private Sort.Order toOrder(String value) {
        String[] parts = value.split(DEFAULT_DELIMETER);
        return new Sort.Order(parts.length > 1 ?
                Sort.Direction.fromString(parts[1]) :
                Sort.Direction.ASC, parts[0]);
    }
}
