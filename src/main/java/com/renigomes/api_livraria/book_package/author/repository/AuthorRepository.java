package com.renigomes.api_livraria.book_package.author.repository;

import com.renigomes.api_livraria.book_package.author.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
