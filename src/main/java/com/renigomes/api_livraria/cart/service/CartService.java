package com.renigomes.api_livraria.cart.service;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.cart.repository.CartRepository;
import com.renigomes.api_livraria.cart.model.ItemCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ItemCartService itemBookService;

    @Autowired
    private CartRepository cartRepository;

    public List<ItemCart> getCartFullItemsByCartID(int cart_id){
        Optional<Cart> cart = cartRepository.findById(cart_id);
        return cart.map(value -> itemBookService.getListItemBookByCart(value)).orElse(null);
    }

}
