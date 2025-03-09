package com.renigomes.api_livraria.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.renigomes.api_livraria.user.model.User}
 */
@AllArgsConstructor
@Getter
public class UserRespDtoTwo implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    private final String name;
    @NotNull
    @NotEmpty
    @NotBlank
    private final String surname;
    @NotNull
    @Email
    @NotEmpty
    @NotBlank
    private final String email;
}