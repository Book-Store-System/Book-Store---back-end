package com.renigomes.api_livraria.book_package.publisher.repository;

import com.renigomes.api_livraria.book_package.publisher.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByName(String name);

}
