package com.renigomes.api_livraria.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.renigomes.api_livraria.book.model.Book}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookRespUserDto implements Serializable {
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
    @NotBlank
    private  String language;
    @NotNull
    private BigDecimal totalPrice;
}