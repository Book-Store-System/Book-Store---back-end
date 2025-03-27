package com.renigomes.api_livraria.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookRespOrderDto {

    @NotBlank
    private  String title;
    @NotBlank
    private  String picture;
    private BigDecimal price;

    
}
