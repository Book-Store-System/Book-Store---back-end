package com.renigomes.api_livraria.delivery_control.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DeliveredRespDto(
        @NotNull Long user_id,
        @NotNull Long order_id,
        @NotBlank String transportation,
        @NotBlank String status,
        @NotNull LocalDate deliveredOn
) {
}
