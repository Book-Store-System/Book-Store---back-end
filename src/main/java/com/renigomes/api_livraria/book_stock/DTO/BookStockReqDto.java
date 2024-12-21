package com.renigomes.api_livraria.book_stock.DTO;

import com.renigomes.api_livraria.book.dto.BookReqDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.renigomes.api_livraria.book_stock.model.BookStock}
 */
@AllArgsConstructor
@Getter
@Setter
public class BookStockReqDto implements Serializable {
    @NotNull
    private  BookReqDto book;
    @NotNull
    @Positive
    private  Long quantityInStock;
    @NotNull
    @Positive
    private BigDecimal purchasePrice;
    @NotNull
    @Positive
    private Double profitMargin;
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