package com.renigomes.api_livraria.address.exceptions;

import org.springframework.http.HttpStatus;

public class AddressNotFound extends AddressException {

    public AddressNotFound(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
