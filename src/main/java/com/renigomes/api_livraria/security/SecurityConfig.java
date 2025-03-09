package com.renigomes.api_livraria.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@SecurityScheme(
        name = SecurityConfig.SECURITY,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT", scheme = "bearer"
)
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    public static final String SECURITY = "bearerAuth";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(SecurityPermission.SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.GET, SecurityPermission.API_PERMISSION_GET_ADMIN).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, SecurityPermission.API_PERMISSION_PERMISSION_ALL_POST).permitAll()
                        .requestMatchers(HttpMethod.GET, SecurityPermission.API_PERMISSION_PERMISSION_ALL_GET).permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityPermission.API_PERMISSION_POST_ADMIN).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, SecurityPermission.API_PERMISSION_PATCH_ADMIN).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
