package com.renigomes.api_livraria.item_book.DTO;

import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.renigomes.api_livraria.item_book.model.ItemBook}
 */
@AllArgsConstructor
@Getter
@Setter
public class ItemBookDto implements Serializable {
    private final BookStockRespUserDto bookStock;
    private final Cart cart;
    private final Integer quantity;
}