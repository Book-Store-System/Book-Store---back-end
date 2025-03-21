package com.renigomes.api_livraria.book.dto;

import com.renigomes.api_livraria.book.model.BookStock;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link BookStock}
 */
@AllArgsConstructor
@Getter
@Setter
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