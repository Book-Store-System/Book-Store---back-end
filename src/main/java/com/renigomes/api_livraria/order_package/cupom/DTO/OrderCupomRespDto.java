package com.renigomes.api_livraria.order_package.cupom.DTO;

import jakarta.validation.constraints.NotBlank;

public record OrderCupomRespDto(@NotBlank String codeCupom) {
}
