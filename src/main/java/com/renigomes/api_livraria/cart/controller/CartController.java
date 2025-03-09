package com.renigomes.api_livraria.cart.controller;

import com.renigomes.api_livraria.book.dto.BookRespUserDTO;
import com.renigomes.api_livraria.cart.service.CartService;
import com.renigomes.api_livraria.item_book.DTO.ItemBookRespDto;
import com.renigomes.api_livraria.item_book.model.ItemBook;
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
    public ResponseEntity<List<ItemBookRespDto>> getCartItemsFull(@PathVariable("id") int id_cart){
        List<ItemBook> itemBooks = cartService.getCartFullItemsByCartID(id_cart);
        return ResponseEntity.ok(
                itemBooks.stream()
                        .map(item -> {
                            ItemBookRespDto itemBookRespDto = modelMapper.map(item, ItemBookRespDto.class);
                            itemBookRespDto.setBook(modelMapper.map(item.getBookStock().getBook(), BookRespUserDTO.class));
                            itemBookRespDto.setTotalPrice(
                                    BigDecimal.valueOf(item.getQuantity())
                                            .multiply(item.getBookStock().getPurchasePrice().add(
                                                    item.getBookStock().getPurchasePrice()
                                                    .multiply(BigDecimal.valueOf(
                                                            item.getBookStock().getProfitMargin()
                                                    ))))
                            );
                            return  itemBookRespDto;
                        }).toList()
        );
    }

    @PostMapping
    public void createCart(){}

}
