package com.renigomes.api_livraria.book.exception;

import org.springframework.http.HttpStatus;

public class OutStockException extends BookException{
    public OutStockException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
