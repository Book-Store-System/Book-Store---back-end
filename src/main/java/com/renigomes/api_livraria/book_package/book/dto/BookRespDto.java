package com.renigomes.api_livraria.book_package.book.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.renigomes.api_livraria.book_package.author.model.Author;
import com.renigomes.api_livraria.book_package.book.model.Book;
import com.renigomes.api_livraria.book_package.category.model.Category;
import com.renigomes.api_livraria.book_package.publisher.model.Publisher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link Book}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookRespDto implements Serializable {
    @NotBlank
    private  String title;
    @NotBlank
    private Author author;
    @NotBlank
    private Category category;
    @NotBlank
    private  String description;
    @NotBlank
    private  String picture;
    @NotBlank
    private  String language;
    @NotBlank
    private Publisher publisher;
    @NotNull
    private LocalDate publicationDate;
    @NotNull
    @Positive
    private  Integer numberOfPages;
    @NotBlank
    private  String dimensions;
    @NotNull
    @Positive
    private String barcode;
}