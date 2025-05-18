package com.renigomes.api_livraria.order_package.cupom.exceptions;

import org.springframework.http.HttpStatus;

public class CupomNotFoundException extends CupomException {
    public CupomNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}