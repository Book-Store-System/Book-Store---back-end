package com.renigomes.api_livraria.delivery_control.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DeliveryNotFoundException extends DeliveryException {

    public DeliveryNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
