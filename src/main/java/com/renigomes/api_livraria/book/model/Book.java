package com.renigomes.api_livraria.book.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "book")
@Setter
@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", unique = true)
    private String title;
    private String author;
    private String genre;
    private String description;
    private String picture;
    private String language;
    private String publisher;
    private LocalDate publicationDate;
    @Column(name="number_of_page")
    private Integer numberOfPages;
    @Column(name="dimension")
    private String dimensions;
    private String barcode;
    private LocalDate registeredOn;
}
