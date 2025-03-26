package com.renigomes.api_livraria.purchase_order.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class OrderException extends RuntimeException {
    private HttpStatus httpStatus;

    public OrderException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
