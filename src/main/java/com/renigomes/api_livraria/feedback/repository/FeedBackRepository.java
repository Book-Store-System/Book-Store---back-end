package com.renigomes.api_livraria.feedback.repository;

import com.renigomes.api_livraria.feedback.model.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
}
