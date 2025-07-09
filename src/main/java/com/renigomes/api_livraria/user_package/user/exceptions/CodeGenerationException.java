package com.renigomes.api_livraria.user_package.user.exceptions;

import org.springframework.http.HttpStatus;

public class CodeGenerationException extends UserException {

  public CodeGenerationException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }
}
