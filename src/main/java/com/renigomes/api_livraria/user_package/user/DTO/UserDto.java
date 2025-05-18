package com.renigomes.api_livraria.user_package.user.DTO;

import com.renigomes.api_livraria.user_package.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDto(@Email @NotBlank String email, @NotBlank String password) implements Serializable {
}