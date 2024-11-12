package com.renigomes.api_livraria.book_stock.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book.dto.BookRespUserDto;
import com.renigomes.api_livraria.book.exception.OutStockException;
import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book.repository.BookRepository;
import com.renigomes.api_livraria.book.util.Utilities;
import com.renigomes.api_livraria.book_stock.DTO.BookStockReqDto;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import com.renigomes.api_livraria.book_stock.repository.BookStockRepository;
import com.renigomes.api_livraria.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookStockService {

    @Autowired
    private Utilities utilities;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public RespIdDto save(BookStockReqDto bookStockReqDto){
        Book book = modelMapper.map(bookStockReqDto.getBook(), Book.class);
        Optional<Book> returnedBook = bookRepository.findByTitle(book.getTitle());
        book = returnedBook.isPresent() ? returnedBook.get():bookRepository.save(book);
        BookStock bookStock = modelMapper.map(bookStockReqDto, BookStock.class);
        bookStock.setBook(book);
        return
          new RespIdDto(
                  bookStockRepository.save(bookStock).getId()
          );
    }

    public List<BookStock> findByBook(Book book){
         return bookStockRepository.findByBook(book);
    }

    public List<BookStockRespUserDto> findAllBooks(){
        List<BookStock> books = bookStockRepository.findAll();
        return utilities.bookOrganizer(books);
    }
}
