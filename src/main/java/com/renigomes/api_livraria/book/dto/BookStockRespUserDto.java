package com.renigomes.api_livraria.book.dto;

import com.renigomes.api_livraria.book.model.BookStock;
import com.renigomes.api_livraria.offer.model.Offer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link BookStock}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookStockRespUserDto implements Serializable {
    @NotNull
    private Long id;
    @NotNull
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
    @NotNull
    private Offer offer;

}