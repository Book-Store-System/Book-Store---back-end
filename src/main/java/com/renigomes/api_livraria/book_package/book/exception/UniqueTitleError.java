package com.renigomes.api_livraria.book_package.book.exception;

import org.springframework.http.HttpStatus;

public class UniqueTitleError extends BookException {
    public UniqueTitleError(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
