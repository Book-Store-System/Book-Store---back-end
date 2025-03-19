package com.renigomes.api_livraria.book_stock.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book.exception.NotFoundException;
import com.renigomes.api_livraria.book.exception.OutStockException;
import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book.repository.BookRepository;
import com.renigomes.api_livraria.book.util.Utilities;
import com.renigomes.api_livraria.book_stock.DTO.BookStockReqDto;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.exception.UniqueTitleError;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import com.renigomes.api_livraria.book_stock.repository.BookStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
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

    @Transactional
    public RespIdDto save(BookStockReqDto bookStockReqDto){
        Book book = modelMapper.map(bookStockReqDto.getBook(), Book.class);
        if (bookRepository.findByTitle(book.getTitle()).isPresent())
            throw new UniqueTitleError("Title already exists", HttpStatus.CONFLICT);
        book.setRegisteredOn(LocalDate.now());
        book =  bookRepository.save(book);
        BookStock bookStock = modelMapper.map(bookStockReqDto, BookStock.class);
        bookStock.setBook(book);
        return
          new RespIdDto(
                  bookStockRepository.save(bookStock).getId()
          );
    }

    public List<BookStockRespUserDto> findAllBooks(){
        List<BookStock> books = bookStockRepository.findAll()
                .stream().filter(i -> !i.isDeleted()).toList();
        List<BookStockRespUserDto> bookOrganizer = utilities.bookOrganizer(books);
        if (bookOrganizer != null) return bookOrganizer;
        log.error("Book out of stock!");
        throw new OutStockException("Book out of stock!", HttpStatus.BAD_REQUEST);
    }

    public BookStock activeBookStock(long id){
        BookStock bookStock = bookStockRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found!");
                    return new NotFoundException("Book not found!", HttpStatus.NOT_FOUND);
                });
        bookStock.setDeleted(false);
        return bookStockRepository.save(bookStock);
    }

    @Transactional
    public BookStock deleteBookStock(long id){
        BookStock bookStock = bookStockRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found!");
                    return new NotFoundException("Book not found!", HttpStatus.NOT_FOUND);
                });
        bookStock.setDeleted(true);
        return bookStockRepository.save(bookStock);
    }
}
