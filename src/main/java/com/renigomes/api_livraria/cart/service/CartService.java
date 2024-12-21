package com.renigomes.api_livraria.cart.service;

import com.renigomes.api_livraria.book.service.BookService;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import com.renigomes.api_livraria.book_stock.service.BookStockService;
import com.renigomes.api_livraria.cart.repository.CartRepository;
import com.renigomes.api_livraria.item_book.DTO.ItemBookDto;
import com.renigomes.api_livraria.item_book.DTO.ItemBookRespDto;
import com.renigomes.api_livraria.item_book.model.ItemBook;
import com.renigomes.api_livraria.item_book.service.ItemBookService;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemBookService itemBookService;

    @Autowired
    private BookService bookService;

    public List<ItemBook> getCartFullItemsByEmail(String email){
         return itemBookService.getListItemBookByCart(
                cartRepository.findCartByUser(
                        (User) userService.findByEmail(email)
                ).orElse(null)
        );
    }

    @Transactional
    public void saveCart(List<ItemBookRespDto> itemBookRespDtos){

        for (ItemBookRespDto itemBookDto: itemBookRespDtos){

        }
        List<BookStockRespUserDto> bookStock = bookService.findBook("");
    }
}
