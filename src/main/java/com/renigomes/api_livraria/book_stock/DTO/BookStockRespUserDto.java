package com.renigomes.api_livraria.book_stock.DTO;

import com.renigomes.api_livraria.book.dto.BookRespUserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.renigomes.api_livraria.book_stock.model.BookStock}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookStockRespUserDto implements Serializable {
    @NotNull
    private  BookRespUserDto book;
    @NotNull
    @Positive
    private  Long quantityInStock;
    @NotBlank
    private  String publisher;
    @NotNull
    private  LocalDate publicationDate;
    @NotNull
    @Positive
    private  Integer numberOfPages;
    @NotBlank
    private  String dimensions;
    @NotNull
    @Positive
    private  Integer barcode;
}