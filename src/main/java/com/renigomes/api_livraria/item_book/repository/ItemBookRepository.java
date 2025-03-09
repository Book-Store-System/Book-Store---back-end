package com.renigomes.api_livraria.item_book.repository;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.item_book.model.ItemBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemBookRepository extends JpaRepository<ItemBook, Integer> {

    List<ItemBook> findItemBookByCart(Cart cart);

}