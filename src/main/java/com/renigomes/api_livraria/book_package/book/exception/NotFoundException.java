package com.renigomes.api_livraria.book_package.book.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BookException{
    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
