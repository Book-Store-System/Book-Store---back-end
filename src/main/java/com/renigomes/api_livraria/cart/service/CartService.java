package com.renigomes.api_livraria.cart.service;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.cart.repository.CartRepository;
import com.renigomes.api_livraria.item_book.DTO.ItemBookReqDto;
import com.renigomes.api_livraria.item_book.model.ItemBook;
import com.renigomes.api_livraria.item_book.service.ItemBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ItemBookService itemBookService;

    @Autowired
    private CartRepository cartRepository;

    public List<ItemBook> getCartFullItemsByCartID(int cart_id){
        Optional<Cart> cart = cartRepository.findById(cart_id);
        return cart.map(value -> itemBookService.getListItemBookByCart(value)).orElse(null);
    }

    @Transactional
    public void saveCart(List<ItemBookReqDto> itemsList, long userID){

    }

}
