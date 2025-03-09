package com.renigomes.api_livraria.item_book.service;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.item_book.model.ItemBook;
import com.renigomes.api_livraria.item_book.repository.ItemBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemBookService {

    @Autowired
    private ItemBookRepository itemBookRepository;

    public List<ItemBook> getListItemBookByCart(Cart cart){
        return itemBookRepository.findItemBookByCart(cart);
    }
}
