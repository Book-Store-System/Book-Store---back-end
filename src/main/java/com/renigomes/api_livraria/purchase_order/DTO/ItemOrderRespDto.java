package com.renigomes.api_livraria.purchase_order.DTO;

import com.renigomes.api_livraria.book.dto.BookRespOrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderRespDto {
    private BookRespOrderDto book;
    private Integer quantity;
    private BigDecimal subTotalItem;
}
