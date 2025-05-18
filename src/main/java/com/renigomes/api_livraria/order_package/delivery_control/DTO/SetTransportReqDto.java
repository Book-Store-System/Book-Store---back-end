package com.renigomes.api_livraria.order_package.delivery_control.DTO;

import jakarta.validation.constraints.NotBlank;

public record SetTransportReqDto(@NotBlank String transportation) {}
