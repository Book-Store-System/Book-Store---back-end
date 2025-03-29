package com.renigomes.api_livraria.delivery_control.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class DeliveryException extends RuntimeException {
    private final HttpStatus httpStatus;
    public DeliveryException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
