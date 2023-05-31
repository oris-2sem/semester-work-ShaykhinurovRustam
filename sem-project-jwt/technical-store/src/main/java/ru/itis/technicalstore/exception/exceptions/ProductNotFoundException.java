package ru.itis.technicalstore.exception.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
public class ProductNotFoundException extends RuntimeException {
    private final Long id;
    private final String message;

    public ProductNotFoundException(Long id) {
        this.id = id;
        this.message = "Товар с ID " + id + " не был найден!";
    }

}

