package com.renigomes.api_livraria.exception_controller;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import com.renigomes.api_livraria.address.exceptions.AddressException;
import com.renigomes.api_livraria.purchase_order.exceptions.OrderException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.renigomes.api_livraria.book.exception.BookException;
import com.renigomes.api_livraria.config.ExceptionConfig;
import com.renigomes.api_livraria.security.security_exception.TokenException;
import com.renigomes.api_livraria.user.exceptions.UserException;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ExceptionConfig exceptionConfig;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("One or more fields invalid.");
        problemDetail.setType(URI.create("http://localhost:8080/error/invalid-fields"));

        Map<String, Object[]> fields = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                error -> ((FieldError)error).getField(),
                                DefaultMessageSourceResolvable::getArguments
                        )
                );

        problemDetail.setProperty("fields", fields);
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(BookException.class)
    public ProblemDetail bookException(BookException e){
        return exceptionConfig.problemDetailConfig(e.getHttpStatus(),
                e.getMessage(),
                "http://localhost:8080/error/book");

    }

    @ExceptionHandler(AddressException.class)
    public ProblemDetail addressException(AddressException e){
        return exceptionConfig.problemDetailConfig(
                e.getHttpStatus(),
                e.getMessage(),
                "http://localhost:8080/error/address");
    }

    @ExceptionHandler(TokenException.class)
    public ProblemDetail tokenException(TokenException e){
        return exceptionConfig.problemDetailConfig(e.getHttpStatus(),
                e.getMessage(), "http://localhost:8080/error/token");
    }

    @ExceptionHandler(UserException.class)
    public ProblemDetail userException(UserException e){
        return exceptionConfig.problemDetailConfig(
                e.getHttpStatus(),
                e.getMessage(),
                "http://localhost:8080/error/user"
        );
    }

    @ExceptionHandler(OrderException.class)
    public ProblemDetail orderException(OrderException e){
        return exceptionConfig.problemDetailConfig(
                e.getHttpStatus(),
                e.getMessage(),
                "http://localhost:8080/error/order"
        );
    }



}
