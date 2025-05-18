package com.renigomes.api_livraria.book_package.author.service;

import com.renigomes.api_livraria.book_package.author.model.Author;
import com.renigomes.api_livraria.book_package.author.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    @Transactional
    public Author save(Author author){
        Optional<Author> optionalAuthor = authorRepository.findByName(author.getName());
        return optionalAuthor.orElseGet(() -> authorRepository.save(author));
    }

}
