package com.renigomes.api_livraria.book_stock.model;

import com.renigomes.api_livraria.book.model.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "book_stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Book book;
    @Column(name = "qnt_stock")
    private Long quantityInStock;
    @Column(name = "profit_margin")
    private double profitMargin;
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @Column(name = "is_deleted", insertable = false)
    private boolean isDeleted;

}
