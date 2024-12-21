package com.renigomes.api_livraria.book.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private String language;
    private String publisher;
    private LocalDate publicationDate;
    private Integer numberOfPages;
    private String dimensions;
    private Integer barcode;
    private LocalDate registeredOn;
}
