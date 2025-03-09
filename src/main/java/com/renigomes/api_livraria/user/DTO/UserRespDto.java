package com.renigomes.api_livraria.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRespDto {
    @NotNull
    private Long id;
    @Email
    @NotBlank
    private String email;
}
