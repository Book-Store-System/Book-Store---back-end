package com.renigomes.api_livraria.item_book.repository;

import com.renigomes.api_livraria.item_book.model.ItemBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBookRepository extends JpaRepository<ItemBook, Integer> {

}