package com.renigomes.api_livraria.user.util;

import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utilites {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public User extractUserByToker(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader == null ? null :
                authHeader.replace("Bearer ", "");
        String suject = tokenService.valueDateToken(token);
        return (User) userRepository.findByEmail(suject);

    }

}
