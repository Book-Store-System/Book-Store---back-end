package com.renigomes.api_livraria.book_stock.repository;

import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookStockRepository extends JpaRepository<BookStock, Long> {

    Optional<BookStock> findByBook(Book book);
}