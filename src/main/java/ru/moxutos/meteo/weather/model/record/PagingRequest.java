package ru.moxutos.meteo.weather.model.record;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

/**
 * Запрос на постраничный поиск
 * @param page страница
 * @param size размер страницы
 * @param sort сортировка
 */
public record PagingRequest(
        @Parameter @Schema(defaultValue = "0", example = "0") int page,
        @Parameter @Schema(defaultValue = "20", example = "20") int size,
        @Parameter List<String> sort) {

    public PagingRequest() {
        this(0, 10, Collections.emptyList());
    }

    public PagingRequest(Integer page, Integer size) {
        this(page, size, Collections.emptyList());
    }
}
