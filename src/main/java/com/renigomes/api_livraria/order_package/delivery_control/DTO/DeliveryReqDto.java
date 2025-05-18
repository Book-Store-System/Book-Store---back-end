package com.renigomes.api_livraria.order_package.delivery_control.DTO;

import jakarta.validation.constraints.NotNull;

public record DeliveryReqDto(
        @NotNull Long userId,
        @NotNull Long orderId
) {
}
