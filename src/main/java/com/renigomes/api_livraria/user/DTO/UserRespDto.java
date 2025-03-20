package com.renigomes.api_livraria.user.DTO;

import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.enums.Status;
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
    @NotNull
    private Status status;
}
