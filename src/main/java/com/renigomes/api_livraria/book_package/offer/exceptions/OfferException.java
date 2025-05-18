package com.renigomes.api_livraria.book_package.offer.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class OfferException extends RuntimeException {
    private HttpStatus httpStatus;

    public OfferException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
