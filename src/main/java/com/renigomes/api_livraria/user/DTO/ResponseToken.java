package com.renigomes.api_livraria.user.DTO;

import jakarta.validation.constraints.NotBlank;

public record ResponseToken(@NotBlank  String token) {
}
