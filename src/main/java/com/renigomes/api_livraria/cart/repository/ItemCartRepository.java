package com.renigomes.api_livraria.cart.repository;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.cart.model.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCartRepository extends JpaRepository<ItemCart, Integer> {

    List<ItemCart> findItemBookByCart(Cart cart);

}