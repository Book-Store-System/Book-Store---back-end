package com.renigomes.api_livraria.book_package.category.service;

import com.renigomes.api_livraria.book_package.category.model.Category;
import com.renigomes.api_livraria.book_package.category.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Transactional
    public Category save(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
        return optionalCategory.orElseGet(() -> categoryRepository.save(category));
    }

}
