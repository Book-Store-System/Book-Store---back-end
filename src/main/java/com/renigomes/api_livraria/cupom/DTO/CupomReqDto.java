package com.renigomes.api_livraria.cupom.DTO;

import com.renigomes.api_livraria.cupom.enums.TypeCupom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CupomReqDto(
        @NotBlank String codeCupom,
        @NotBlank String description,
        @NotNull
        TypeCupom typeCupom,
        @NotNull
        @Positive
        Double percentDiscount
) {
}
