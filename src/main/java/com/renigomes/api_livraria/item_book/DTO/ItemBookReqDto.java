package com.renigomes.api_livraria.item_book.DTO;

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
public class ItemBookReqDto {
    private BookReqUSerDto book;
    private  Integer quantity;
    private BigDecimal totalPrice;
}
