package com.renigomes.api_livraria.order_package.purchase_order.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ItemOrderException extends OrderException {

    public ItemOrderException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
