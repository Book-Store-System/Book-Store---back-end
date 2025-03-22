package com.renigomes.api_livraria.cart.controller;

import com.renigomes.api_livraria.book.dto.BookRespUserDTO;
import com.renigomes.api_livraria.cart.service.CartService;
import com.renigomes.api_livraria.cart.DTO.ItemCartRespDto;
import com.renigomes.api_livraria.cart.model.ItemCart;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @GetMapping("{id}/items_cart")
    public ResponseEntity<List<ItemCartRespDto>> getCartItemsFull(@PathVariable("id") int id_cart){
        List<ItemCart> itemBooks = cartService.getCartFullItemsByCartID(id_cart);
        return ResponseEntity.ok(
                itemBooks.stream()
                        .map(item -> {
                            ItemCartRespDto itemCartRespDto = modelMapper.map(item, ItemCartRespDto.class);
                            itemCartRespDto.setBook(modelMapper.map(item.getBookStock().getBook(), BookRespUserDTO.class));
                            itemCartRespDto.setTotalPrice(
                                    BigDecimal.valueOf(item.getQuantity())
                                            .multiply(item.getBookStock().getPurchasePrice().add(
                                                    item.getBookStock().getPurchasePrice()
                                                    .multiply(BigDecimal.valueOf(
                                                            item.getBookStock().getProfitMargin()
                                                    ))))
                            );
                            return itemCartRespDto;
                        }).toList()
        );
    }

    @PostMapping
    public void createCart(){}

}
