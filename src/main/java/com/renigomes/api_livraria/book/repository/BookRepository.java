package com.renigomes.api_livraria.book.repository;

import com.renigomes.api_livraria.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM book WHERE LOWER(title) LIKE LOWER(:search) OR LOWER(author) LIKE LOWER(:search2) OR LOWER(genre) LIKE LOWER(:search3)", nativeQuery = true)
    List<Book> findByTitleOrAuthorOrGenre(String search, String search2, String search3);

    Optional<Book> findByTitle(String title);
}