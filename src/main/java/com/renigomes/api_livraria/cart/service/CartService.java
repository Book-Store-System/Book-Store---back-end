package com.renigomes.api_livraria.cart.service;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.cart.repository.CartRepository;
import com.renigomes.api_livraria.item_book.model.ItemBook;
import com.renigomes.api_livraria.item_book.service.ItemBookService;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemBookService itemBookService;

    public List<ItemBook> getCartFullItemsByEmail(String email){
         return itemBookService.getListItemBookByCart(
                cartRepository.findCartByUser(
                        userService.findByEmail(email)
                ).orElse(null)
        );
    }
}
