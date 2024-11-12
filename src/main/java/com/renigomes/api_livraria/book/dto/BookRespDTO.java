package com.renigomes.api_livraria.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookRespDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String titulo;
    @NotNull
    private BigDecimal purchasePrice;
    private String picture;
}
