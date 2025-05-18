package com.renigomes.api_livraria.order_package.purchase_order.exceptions;

import org.springframework.http.HttpStatus;

public class OrderNotFound extends OrderException {

    public OrderNotFound(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
