package com.renigomes.api_livraria.book_package.book.exception;

import org.springframework.http.HttpStatus;

public class BookOfferInative extends BookException{

    public BookOfferInative(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
