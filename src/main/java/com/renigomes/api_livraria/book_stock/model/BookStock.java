package com.renigomes.api_livraria.book_stock.model;

import com.renigomes.api_livraria.book.model.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "book_stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Book book;
    @Column(name = "qnt_stock")
    private Long quantityInStock;
    private String publisher;
    private LocalDate publicationDate;
    private Integer numberOfPages;
    private String dimensions;
    private Integer barcode;
}
