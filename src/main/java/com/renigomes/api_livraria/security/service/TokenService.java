package com.renigomes.api_livraria.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.renigomes.api_livraria.security.security_exception.TokenException;
import com.renigomes.api_livraria.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
public class TokenService {

    @Value("${api.secret.key}")
    private String secret;
    private Algorithm algorithm;

    public String generateToken(User user){
        try{
            algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-user")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException e) {
            log.error(e.getMessage());
            throw new TokenException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String valueDateToken(String token){
        algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("auth-user")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant genExpirationDate(){
        return LocalDateTime
                .now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
