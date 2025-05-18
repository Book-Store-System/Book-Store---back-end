package com.renigomes.api_livraria.user_package.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserEditReqDTO {
    @NotBlank
    public String name;
    @NotBlank
    public String surname;
    @NotBlank
    @Email
    public String email;
}
