package com.renigomes.api_livraria.security.security_exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenException extends RuntimeException{
    private HttpStatus httpStatus;

    public TokenException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
