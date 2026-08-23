package ru.moxutos.meteo.weather.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.moxutos.meteo.weather.model.record.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String BAD_REQUEST = "Введены не корректные данные в запросе.";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException exception
    ) {
        log.warn("Ошибка в пользовательского ввода: {}", exception.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(BAD_REQUEST, exception.getMessage()));
    }

}
