package com.renigomes.api_livraria.item_book.model;

import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private Integer quantity;
    private BigDecimal subtotal;

}
