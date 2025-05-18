package com.renigomes.api_livraria.order_package.cupom.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class CupomRespDto {
    @NotBlank
    private String codeCupom;
    @NotBlank
    private String description;
    @NotNull
    @Positive
    private Double percentDiscount;
}
