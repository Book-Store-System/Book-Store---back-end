package com.renigomes.api_livraria.user.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class UserException extends RuntimeException{
    private final HttpStatus httpStatus;

    public UserException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
