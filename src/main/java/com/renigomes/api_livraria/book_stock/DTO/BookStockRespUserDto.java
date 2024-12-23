package com.renigomes.api_livraria.book_stock.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

import com.renigomes.api_livraria.book.dto.BookRespUserDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotNull
    @Positive
    private double profitMargin;
    @NotNull
    @Positive
    private BigDecimal purchasePrice;
    @NotNull
    @Positive
    private BigDecimal salePrice;

}