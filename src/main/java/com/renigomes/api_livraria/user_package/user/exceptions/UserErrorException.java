package com.renigomes.api_livraria.user_package.user.exceptions;

import org.springframework.http.HttpStatus;

public class UserErrorException extends UserException{
    public UserErrorException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
