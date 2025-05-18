package com.renigomes.api_livraria.user_package.address.exceptions;

import org.springframework.http.HttpStatus;

public class AddressUpdateError extends AddressException {

    public AddressUpdateError(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
