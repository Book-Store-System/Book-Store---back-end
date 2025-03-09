package com.renigomes.api_livraria.book.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BookException extends RuntimeException{
    private HttpStatus httpStatus;

    public BookException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
