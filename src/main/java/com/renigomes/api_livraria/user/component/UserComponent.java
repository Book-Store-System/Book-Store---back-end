package com.renigomes.api_livraria.user.component;

import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserComponent {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public User extractUserByToker(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader == null ? null :
                authHeader.replace("Bearer ", "");
        String suject = tokenService.valueDateToken(token);
        return (User) userRepository.findByEmail(suject);

    }
}
