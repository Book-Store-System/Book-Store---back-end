package com.renigomes.api_livraria.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.renigomes.api_livraria.user.model.User}
 */
public record UserDto(@Email @NotBlank String email, @NotBlank String password) implements Serializable {
}