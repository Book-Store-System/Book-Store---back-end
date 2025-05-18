package com.renigomes.api_livraria.order_package.feedback.repository;

import com.renigomes.api_livraria.book_package.book.model.BookStock;
import com.renigomes.api_livraria.order_package.feedback.model.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {

    List<FeedBack> findByBookStock(BookStock bookStock);
}
