package com.renigomes.api_livraria.book_stock.exception;

import com.renigomes.api_livraria.book.exception.BookException;
import org.springframework.http.HttpStatus;

public class UniqueTitleError extends BookException {
    public UniqueTitleError(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
