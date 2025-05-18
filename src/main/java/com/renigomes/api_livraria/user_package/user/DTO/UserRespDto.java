package com.renigomes.api_livraria.user_package.user.DTO;

import com.renigomes.api_livraria.user_package.user.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRespDto {
    @NotBlank
    private Long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    private Role role;
}
