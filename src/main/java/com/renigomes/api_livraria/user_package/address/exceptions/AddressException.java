package com.renigomes.api_livraria.user_package.address.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AddressException extends RuntimeException {
  private final HttpStatus httpStatus;

  public AddressException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
