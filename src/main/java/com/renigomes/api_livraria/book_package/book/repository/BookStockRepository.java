package com.renigomes.api_livraria.book_package.book.repository;

import com.renigomes.api_livraria.book_package.book.model.Book;
import com.renigomes.api_livraria.book_package.book.model.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookStockRepository extends JpaRepository<BookStock, Long> {

    Optional<BookStock> findByBook(Book book);
}