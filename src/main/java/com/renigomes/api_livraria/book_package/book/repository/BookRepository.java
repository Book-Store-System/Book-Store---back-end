package com.renigomes.api_livraria.book_package.book.repository;

import com.renigomes.api_livraria.book_package.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT book.id, title, description, language, number_of_page," +
            " author_id, category_id, publisher_id," +
            "picture, publication_date, barcode, dimension, registered_on" +
            " FROM book JOIN public.author a " +
            "on book.author_id = a.id JOIN author ON book.author_id = author.id\n" +
            "JOIN category ON book.category_id = category.id\n" +
            "JOIN publisher ON book.publisher_id = publisher.id\n" +
            "WHERE category.name ILIKE :search\n" +
            "   OR author.name ILIKE :search2\n" +
            "   OR publisher.name ILIKE :search3\n" +
            "   OR title ILIKE :search4;", nativeQuery = true)
    List<Book> findByCategoryOrAuthorOrPublisherOrTitle(String search, String search2, String search3, String search4);

    Optional<Book> findByTitle(String title);

    @Query(value = "SELECT " +
            "book.id, " +
            "title, " +
            "description, " +
            "language, " +
            "number_of_page, " +
            "author_id, " +
            "category_id, " +
            "publisher_id, " +
            "picture, " +
            "publication_date, " +
            "barcode, " +
            "dimension, " +
            "registered_on " +
            "FROM book JOIN category ON book.category_id = category.id " +
            "WHERE category.name ILIKE :category;" , nativeQuery = true)
    List<Book> findByCategory_Name(String category);
}