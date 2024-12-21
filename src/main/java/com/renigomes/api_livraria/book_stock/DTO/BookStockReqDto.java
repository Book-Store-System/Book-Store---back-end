package com.renigomes.api_livraria.book_stock.DTO;

import com.renigomes.api_livraria.book.dto.BookReqDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.renigomes.api_livraria.book_stock.model.BookStock}
 */
@AllArgsConstructor
@Getter
public class BookStockReqDto implements Serializable {
    @NotNull
    private final BookReqDto book;
    @NotNull
    @Positive
    private final Long quantityInStock;
    @NotNull
    @Positive
    private double profitMargin;
    @NotNull
    @Positive
    private BigDecimal purchasePrice;
}