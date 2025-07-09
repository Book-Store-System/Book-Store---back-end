package com.renigomes.api_livraria.user_package.user.exceptions;

import org.springframework.http.HttpStatus;

public class SendEmailException extends UserException {
    public SendEmailException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
