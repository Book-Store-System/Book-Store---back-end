package com.renigomes.api_livraria.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Integrated BookStore System")
                        .description("""
                                Project for studying Spring Boot.
                                This project aims to apply all my current
                                knowledge about the Java programming language
                                and the Spring Boot framework, as well as
                                my knowledge in API development, testing, and documentation
                                """).version("V.0.0.1")
                ).externalDocs(new ExternalDocumentation()
                        .description("Link to the project repository.")
                        .url("https://github.com"));

    }

}
