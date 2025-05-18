package com.renigomes.api_livraria.order_package.purchase_order.exceptions;

import org.springframework.http.HttpStatus;

public class InternalOrderError extends OrderException {

    public InternalOrderError(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
