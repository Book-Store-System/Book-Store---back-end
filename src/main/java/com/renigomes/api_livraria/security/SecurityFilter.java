package com.renigomes.api_livraria.security;

import com.renigomes.api_livraria.security.component.TokenComponent;
import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user_package.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private UserRepository userRepository;
    private TokenService tokenService;
    private TokenComponent tokenComponent;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenComponent.recorverToken(request);
        if(token != null){
            String subject = tokenService.valueDateToken(token);
            UserDetails userDetails = userRepository.findByEmail(subject);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
