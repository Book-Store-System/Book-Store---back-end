package com.renigomes.api_livraria.book_stock.DTO;

import com.renigomes.api_livraria.book.dto.BookRespDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.renigomes.api_livraria.book_stock.model.BookStock}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookStockRespUserDto implements Serializable {
    private BookRespDto book;
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