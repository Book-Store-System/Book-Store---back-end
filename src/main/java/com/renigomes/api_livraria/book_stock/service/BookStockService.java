package com.renigomes.api_livraria.book_stock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renigomes.api_livraria.DTO.RespIdDto;
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

    @Transactional
    public RespIdDto save(BookStockReqDto bookStockReqDto){
        Book book = modelMapper.map(bookStockReqDto.getBook(), Book.class);
        Optional<Book> returnedBook = bookRepository.findByTitle(book.getTitle());
        if (returnedBook.isPresent()) {
            book = returnedBook.get();
            if (bookStockRepository.findByBook(book).isPresent()){
                BookStock bookStock = bookStockRepository.findByBook(book).get();
                bookStock.setQuantityInStock(
                        bookStock.getQuantityInStock()+bookStockReqDto.getQuantityInStock()
                );
                bookStock.setProfitMargin(bookStockReqDto.getProfitMargin());
                bookStock.setPurchasePrice(bookStockReqDto.getPurchasePrice());
                bookStock.setBook(book);
                bookStock.setLastPurchase(LocalDate.now());
                return
                        new RespIdDto(
                                bookStockRepository.save(bookStock).getId()
                        );
            }
        }else{
            book.setRegisteredOn(LocalDate.now());
            book = bookRepository.save(book);
        }
        BookStock bookStock = modelMapper.map(bookStockReqDto, BookStock.class);
        bookStock.setBook(book);
        bookStock.setLastPurchase(LocalDate.now());
        return
          new RespIdDto(
                  bookStockRepository.save(bookStock).getId()
          );
    }

    public List<BookStockRespUserDto> findAllBooks(){
        List<BookStock> books = bookStockRepository.findAll();
        List<BookStockRespUserDto> bookOrganizer = utilities.bookOrganizer(books);
        if (bookOrganizer != null) return bookOrganizer;
        log.error("Book out of stock!");
        throw new OutStockException("Book out of stock!", HttpStatus.BAD_REQUEST);
    }
}
