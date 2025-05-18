package com.renigomes.api_livraria.order_package.cupom.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InternalErrorCupomException extends CupomException{
    public InternalErrorCupomException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
