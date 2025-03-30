package com.renigomes.api_livraria.cupom.DTO;

import jakarta.validation.constraints.NotBlank;

public record OrderCupomRespDto(@NotBlank String codeCupom) {
}
