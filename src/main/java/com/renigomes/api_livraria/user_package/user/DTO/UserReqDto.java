package com.renigomes.api_livraria.user_package.user.DTO;

import com.renigomes.api_livraria.user_package.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@AllArgsConstructor
@Getter
@Setter
public class UserReqDto implements Serializable {
    @NotBlank
    private final String name;
    @NotBlank
    private final String surname;
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;
}