package com.renigomes.api_livraria.offer.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class OfferNotFoundException extends OfferException{
    public OfferNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
