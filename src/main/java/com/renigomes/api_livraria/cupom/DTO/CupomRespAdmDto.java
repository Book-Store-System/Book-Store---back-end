package com.renigomes.api_livraria.cupom.DTO;

import com.renigomes.api_livraria.cupom.enums.TypeCupom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CupomRespAdmDto {
    @NotNull
    private Long id;
    @NotBlank
    private String codeCupom;
    @NotBlank
    private String description;
    @NotNull
    private Boolean active;
    @NotNull
    private TypeCupom typeCupom;
    @NotNull
    @Positive
    private Double percentDiscount;
}
