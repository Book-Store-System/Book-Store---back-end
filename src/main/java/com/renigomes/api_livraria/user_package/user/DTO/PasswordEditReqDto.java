package com.renigomes.api_livraria.user_package.user.DTO;

import jakarta.validation.constraints.NotBlank;

public record PasswordEditReqDto(
        @NotBlank String newPassword,
        @NotBlank String repeatNewPassword) {}
