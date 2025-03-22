package com.renigomes.api_livraria.cart.DTO;

import com.renigomes.api_livraria.book.dto.BookReqUSerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemCartReqDto {
    private BookReqUSerDto book;
    private  Integer quantity;
    private BigDecimal totalPrice;
}
