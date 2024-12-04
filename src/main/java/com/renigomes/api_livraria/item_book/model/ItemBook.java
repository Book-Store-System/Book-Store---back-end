package com.renigomes.api_livraria.item_book.model;

import com.renigomes.api_livraria.book_stock.model.BookStock;
import com.renigomes.api_livraria.cart.model.Cart;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "item_book")
@NoArgsConstructor
@Getter
@Setter
public class ItemBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private BookStock bookStock;
    @ManyToOne
    private Cart cart;
    private Integer quantity;

}
