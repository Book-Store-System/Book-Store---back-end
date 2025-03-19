package com.renigomes.api_livraria.book_stock.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book.component.BookComponent;
import com.renigomes.api_livraria.book.exception.NotFoundException;
import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book.repository.BookRepository;
import com.renigomes.api_livraria.book_stock.DTO.BookStockReqDto;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.exception.UniqueTitleError;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import com.renigomes.api_livraria.book_stock.repository.BookStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class BookStockService {

    private static final String BOOK_OUT_OF_STOCK = "Book out of stock!";
    @Autowired
    private BookComponent bookComponent;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStockRepository bookStockRepository;

    private static NotFoundException get() {
        log.error(BOOK_OUT_OF_STOCK);
        return new NotFoundException(BOOK_OUT_OF_STOCK, HttpStatus.NOT_FOUND);
    }

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
        List<BookStockRespUserDto> bookOrganizer = bookComponent.bookOrganizer(books);
        if (bookOrganizer != null) return bookOrganizer;
        throw get();
    }

    @Transactional
    public BookStock updateBookStock(BookStockReqDto bookStockReqDto, long id_book_stock){
       BookStock bookStock = bookStockRepository.findById(id_book_stock).orElseThrow(
               BookStockService::get
       );
        BeanUtils.copyProperties(bookStockReqDto.getBook(), bookStock.getBook());
        BeanUtils.copyProperties(bookStockReqDto, bookStock);
        bookStock = bookStockRepository.save(bookStock);
        return bookStock;
    }

    public BookStock activeBookStock(long id){
        BookStock bookStock = bookStockRepository.findById(id)
                .orElseThrow(
                    BookStockService::get
                );
        bookStock.setDeleted(false);
        return bookStockRepository.save(bookStock);
    }

    @Transactional
    public BookStock deleteBookStock(long id){
        BookStock bookStock = bookStockRepository.findById(id)
                .orElseThrow(BookStockService::get);
        bookStock.setDeleted(true);
        return bookStockRepository.save(bookStock);
    }
}
