package com.renigomes.api_livraria.user_package.address.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserHasNoAddressException extends AddressException{
    public UserHasNoAddressException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
