package com.renigomes.api_livraria.user.DTO;

import jakarta.validation.constraints.NotBlank;

public record PasswordEditReqDto(
        @NotBlank String newPassword,
        @NotBlank String repeatNewPassword) {}
