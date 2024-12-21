package com.renigomes.api_livraria.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    @NotBlank
    private String publisher;
    @NotNull
    private LocalDate publicationDate;
    @NotNull
    @Positive
    private Integer numberOfPages;
    @NotBlank
    private String dimensions;
    @NotNull
    @Positive
    private Integer barcode;
}