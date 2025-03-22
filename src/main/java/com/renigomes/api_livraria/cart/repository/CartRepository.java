package com.renigomes.api_livraria.cart.repository;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}