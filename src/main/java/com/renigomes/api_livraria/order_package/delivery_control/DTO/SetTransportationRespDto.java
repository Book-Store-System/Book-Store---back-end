package com.renigomes.api_livraria.order_package.delivery_control.DTO;

import com.renigomes.api_livraria.order_package.delivery_control.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SetTransportationRespDto(
        @NotNull Long user_id,
        @NotNull Long order_id,
        @NotBlank String transportation,
        @NotNull OrderStatus status
){
        }
