package com.renigomes.api_livraria.book.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "book")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String picture;
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @Column(name = "profit_margin")
    private Double profitMargin;
    private String language;
}
