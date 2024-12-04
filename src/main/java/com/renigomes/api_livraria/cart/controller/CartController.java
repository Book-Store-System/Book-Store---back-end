package com.renigomes.api_livraria.cart.controller;

import com.renigomes.api_livraria.cart.service.CartService;
import com.renigomes.api_livraria.item_book.DTO.ItemBookDto;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Cart")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@RequestMapping("api/cart")
@RestController
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "Product into the cart",
            description = "Method to get full items into the cart"
    )
    @GetMapping
    public ResponseEntity<List<ItemBookDto>> getCartItemsFull(@RequestParam("email") String email){
        return ResponseEntity.ok(
                cartService.getCartFullItemsByEmail(email)
                        .stream()
                        .map(i -> modelMapper.map(i, ItemBookDto.class))
                        .toList()
        );
    }

}
