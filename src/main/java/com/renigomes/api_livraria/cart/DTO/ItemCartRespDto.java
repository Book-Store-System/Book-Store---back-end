package com.renigomes.api_livraria.cart.DTO;

import com.renigomes.api_livraria.book.dto.BookRespUserDTO;
import com.renigomes.api_livraria.cart.model.ItemCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ItemCart}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemCartRespDto implements Serializable {
    private  BookRespUserDTO book;
    private  Integer quantity;
    private BigDecimal totalPrice;
}