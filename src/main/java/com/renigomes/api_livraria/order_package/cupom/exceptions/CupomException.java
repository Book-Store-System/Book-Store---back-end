package com.renigomes.api_livraria.order_package.cupom.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class CupomException extends RuntimeException {
    private final HttpStatus httpStatus;
    public CupomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
