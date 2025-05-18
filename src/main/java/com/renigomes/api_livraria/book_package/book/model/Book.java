package com.renigomes.api_livraria.book_package.book.model;

import java.time.LocalDate;

import com.renigomes.api_livraria.book_package.author.model.Author;
import com.renigomes.api_livraria.book_package.category.model.Category;
import com.renigomes.api_livraria.book_package.publisher.model.Publisher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "book")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", unique = true)
    private String title;
    //modify to author class
    @ManyToOne
    private Author author;
    //modify to category class
    @ManyToOne
    private Category category;
    private String description;
    private String picture;
    private String language;
    //modify to publisher class
    @ManyToOne
    private Publisher publisher;
    private LocalDate publicationDate;
    @Column(name="number_of_page")
    private Integer numberOfPages;
    @Column(name="dimension")
    private String dimensions;
    private String barcode;
    private LocalDate registeredOn;
}
