package com.renigomes.api_livraria.user_package.user.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends UserException{
    public UnauthorizedUserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
