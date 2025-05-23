package com.renigomes.api_livraria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;

@Configuration
public class ExceptionConfig {

    public ProblemDetail problemDetailConfig(HttpStatus httpStatus, String error, String uri){
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(httpStatus);
        problemDetail.setTitle(error);
        problemDetail.setType(URI.create(uri));
        return problemDetail;
    }

}
