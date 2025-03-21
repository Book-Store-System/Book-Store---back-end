package com.renigomes.api_livraria.book.service;

import com.renigomes.api_livraria.book.exception.NotFoundException;
import com.renigomes.api_livraria.book.exception.OutStockException;
import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book.repository.BookRepository;
import com.renigomes.api_livraria.book.component.BookComponent;
import com.renigomes.api_livraria.book.model.BookStock;
import com.renigomes.api_livraria.book.repository.BookStockRepository;
import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookComponent bookComponent;

    public  List<?> findBook(String search, HttpServletRequest request){
        search = "%"+search+"%";
        List<Book> listBook = bookRepository.findByTitleOrAuthorOrGenre(search, search, search);
        if (!listBook.isEmpty()){
            List<BookStock> listStockBook;
            User user = userService.extractUserByToker(request);
            if(user.getRole() == Role.ADMIN)
                listStockBook = listBook
                    .stream()
                    .map(book -> bookStockRepository.findByBook(book).get())
                    .toList();
            else listStockBook = listBook
                    .stream()
                    .map(book -> bookStockRepository.findByBook(book).get())
                    .filter(bookStock -> !bookStock.isDeleted())
                    .toList();

            List<?> bookOrganizer = bookComponent.bookOrganizer(listStockBook, user);
            if (bookOrganizer != null) return bookOrganizer;
            log.error("Book out of stock!");
            throw new OutStockException("Book out of stock!", HttpStatus.BAD_REQUEST);
        }
        log.error("Book not found!");
        throw new NotFoundException("Book not found!", HttpStatus.BAD_REQUEST);
    }
}
