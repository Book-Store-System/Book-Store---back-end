package com.renigomes.api_livraria.item_book.DTO;

import com.renigomes.api_livraria.book.dto.BookRespUserDTO;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.renigomes.api_livraria.item_book.model.ItemBook}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemBookRespDto implements Serializable {
    private  BookRespUserDTO book;
    private  Integer quantity;
    private BigDecimal totalPrice;
}