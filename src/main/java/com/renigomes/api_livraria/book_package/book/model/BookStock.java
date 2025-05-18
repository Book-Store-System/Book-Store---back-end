package com.renigomes.api_livraria.book_package.book.model;

import com.renigomes.api_livraria.book_package.offer.model.Offer;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

}
