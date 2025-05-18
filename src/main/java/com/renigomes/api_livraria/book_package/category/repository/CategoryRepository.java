package com.renigomes.api_livraria.book_package.category.repository;

import com.renigomes.api_livraria.book_package.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query methods can be defined here if needed
    Optional<Category> findByName(String name);
}
