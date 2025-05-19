package com.renigomes.api_livraria.security.component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenComponent {

    public String recorverToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        return authHeader == null ? null :
                authHeader.replace("Bearer ", "");
    }
}
