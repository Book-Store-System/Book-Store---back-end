package com.renigomes.api_livraria.book_package.publisher.service;

import com.renigomes.api_livraria.book_package.publisher.model.Publisher;
import com.renigomes.api_livraria.book_package.publisher.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PublisherService {

    private PublisherRepository publisherRepository;

    @Transactional
    public Publisher save(Publisher publisher){
        Optional<Publisher> publisherToSave = publisherRepository.findByName(publisher.getName());
        return publisherToSave.orElseGet(() -> publisherRepository.save(publisher));
    }
}
