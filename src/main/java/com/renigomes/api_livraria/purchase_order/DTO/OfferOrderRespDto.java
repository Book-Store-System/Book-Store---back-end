package com.renigomes.api_livraria.purchase_order.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferOrderRespDto {

    @NotBlank
    private String title;
    @NotNull
    private Double percent;
}
