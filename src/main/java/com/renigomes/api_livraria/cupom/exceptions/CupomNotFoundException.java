package com.renigomes.api_livraria.cupom.exceptions;

import org.springframework.http.HttpStatus;

public class CupomNotFoundException extends CupomException {
    public CupomNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}