package com.renigomes.api_livraria.feedback.repository;

import com.renigomes.api_livraria.book.model.BookStock;
import com.renigomes.api_livraria.feedback.model.FeedBack;
import com.renigomes.api_livraria.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {

    List<FeedBack> findByBookStock(BookStock bookStock);
}
