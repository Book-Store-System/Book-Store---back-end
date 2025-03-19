package com.renigomes.api_livraria.book.service;

import com.renigomes.api_livraria.book.exception.NotFoundException;
import com.renigomes.api_livraria.book.exception.OutStockException;
import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book.repository.BookRepository;
import com.renigomes.api_livraria.book.util.Utilities;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import com.renigomes.api_livraria.book_stock.repository.BookStockRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    private Utilities utilities;

    public  List<BookStockRespUserDto> findBook(String search){
        search = "%"+search+"%";
        List<Book> listBook = bookRepository.findByTitleOrAuthorOrGenre(search, search, search);
        if (!listBook.isEmpty()){
            List<BookStock> listStockBook = listBook
                    .stream()
                    .map(book -> bookStockRepository.findByBook(book).get())
                    .toList();

            List<BookStockRespUserDto> bookOrganizer = utilities.bookOrganizer(listStockBook);
            if (bookOrganizer != null) return bookOrganizer;
            log.error("Book out of stock!");
            throw new OutStockException("Book out of stock!", HttpStatus.BAD_REQUEST);
        }
        log.error("Book not found!");
        throw new NotFoundException("Book not found!", HttpStatus.BAD_REQUEST);
    }


}
