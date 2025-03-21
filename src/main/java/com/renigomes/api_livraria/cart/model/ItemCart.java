package com.renigomes.api_livraria.cart.model;

import com.renigomes.api_livraria.book.model.BookStock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "item_cart")
@NoArgsConstructor
@Getter
@Setter
public class ItemCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private BookStock bookStock;
    @ManyToOne
    private Cart cart;
    private Integer quantity;
    @Column
    private Boolean isDeleted;

}
