package com.renigomes.api_livraria.book_package.book.exception;

import org.springframework.http.HttpStatus;

public class BookDeleteError extends BookException{
    public BookDeleteError(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
