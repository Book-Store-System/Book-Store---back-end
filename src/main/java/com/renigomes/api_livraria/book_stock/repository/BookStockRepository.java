package com.renigomes.api_livraria.book_stock.repository;

import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookStockRepository extends JpaRepository<BookStock, Long> {

    List<BookStock> findByBook(Book book);
}