package com.renigomes.api_livraria.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.renigomes.api_livraria.book.model.Book}
 */
@AllArgsConstructor
@Getter
@Setter
public class BookReqDto implements Serializable {
    @NotBlank
    private  String title;
    @NotBlank
    private  String author;
    @NotBlank
    private  String genre;
    @NotBlank
    private  String description;
    @NotBlank
    private  String picture;
    @NotNull
    @Positive
    private  BigDecimal purchasePrice;
    @NotNull
    @Positive
    private  Double profitMargin;
    @NotBlank
    private String language;


}