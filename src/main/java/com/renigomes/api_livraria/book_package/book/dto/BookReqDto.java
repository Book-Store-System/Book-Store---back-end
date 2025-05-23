package com.renigomes.api_livraria.book_package.book.dto;

import com.renigomes.api_livraria.book_package.book.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Book}
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
    private  String category;
    @NotBlank
    private  String description;
    @NotBlank
    private  String picture;
    @NotBlank
    private String language;
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
    private String barcode;


}