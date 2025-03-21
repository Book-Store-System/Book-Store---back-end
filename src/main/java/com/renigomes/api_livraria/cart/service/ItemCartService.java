package com.renigomes.api_livraria.cart.service;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.cart.model.ItemCart;
import com.renigomes.api_livraria.cart.repository.ItemCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCartService {

    @Autowired
    private ItemCartRepository itemCartRepository;

    public List<ItemCart> getListItemBookByCart(Cart cart){
        return itemCartRepository.findItemBookByCart(cart);
    }
}
