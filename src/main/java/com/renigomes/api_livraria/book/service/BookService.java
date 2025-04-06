package com.renigomes.api_livraria.book.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.renigomes.api_livraria.book.component.BookComponent;
import com.renigomes.api_livraria.book.exception.NotFoundException;
import com.renigomes.api_livraria.book.exception.OutStockException;
import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book.model.BookStock;
import com.renigomes.api_livraria.book.repository.BookRepository;
import com.renigomes.api_livraria.book.repository.BookStockRepository;
import com.renigomes.api_livraria.user.component.UserComponent;
import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.model.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BookStockRepository bookStockRepository;
    private UserComponent userComponent;
    private BookComponent bookComponent;

    private List<?> getBookStock(List<Book> listBook, HttpServletRequest request){
        if (!listBook.isEmpty()){
            List<BookStock> listStockBook;
            if (request.getUserPrincipal() == null){
                listStockBook = listBook
                        .stream()
                        .map(book -> bookStockRepository.findByBook(book).get())
                        .filter(bookStock -> !bookStock.isDeleted())
                        .toList();
                return bookComponent.bookOrganizerAll(listStockBook);
            }
            User user = userComponent.extractUserByToker(request);
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

    public List<?> filterBookByGenre(String genre, HttpServletRequest request){
        List<Book> listBook = bookRepository.findByGenre(genre);
        return getBookStock(listBook, request);
    }

    public  List<?> findBook(String search, HttpServletRequest request){
        search = "%"+search+"%";
        List<Book> listBook = bookRepository.findByTitleOrAuthorOrGenre(search, search, search);
        return getBookStock(listBook, request);
    }
}
