package com.renigomes.api_livraria.user_package.user.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class UserNotFoundException extends UserException{

    public UserNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
