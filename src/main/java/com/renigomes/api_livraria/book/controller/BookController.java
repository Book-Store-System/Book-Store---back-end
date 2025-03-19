package com.renigomes.api_livraria.book.controller;

import java.util.List;

import com.renigomes.api_livraria.book.exception.BookDeleteError;
import com.renigomes.api_livraria.book.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book.service.BookService;
import com.renigomes.api_livraria.book_stock.DTO.BookStockReqDto;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.service.BookStockService;
import com.renigomes.api_livraria.security.SecurityConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/book")
@Tag(name = "book")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookStockService bookStockService;

    @Operation(
            summary = "Find all book stock",
            description = "Method to find all book stock"
    )
    @GetMapping
    public ResponseEntity<List<BookStockRespUserDto>> getAllBookStock(){
        return ResponseEntity.ok(bookStockService.findAllBooks());
    }
    @Operation(
            summary = "Book search",
            description = "Performs search for books by author, title or genre"
    )
    @GetMapping("/search")
    public ResponseEntity<List<BookStockRespUserDto>> findBySearch(@RequestParam String search) {
        return ResponseEntity.ok(bookService.findBook(search));
    }
    @Operation(
            summary = "Create stock and book",
            description = "Stock to books"
    )
    @PostMapping
    public ResponseEntity<RespIdDto> createBook(@RequestBody @Valid BookStockReqDto bookStockReqDto) {
        return ResponseEntity.ok(bookStockService.save(bookStockReqDto));
    }

    @Operation(
            summary = "Activate book",
            description = "Activate book by id"
    )
    @PatchMapping("/activate/{id_book_stock}")
    public ResponseEntity<Void> activateBook(@PathVariable(name = "id_book_stock") long id){
        if (bookStockService.activeBookStock(id) != null)
            return ResponseEntity.noContent().build();
        log.error("Book stock could not be activated!");
        throw new BookDeleteError("Book stock could not be deleted!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Update book",
            description = "Method to update a book by id"
    )
    @PutMapping("/update_book/{id_book_stock}")
    public ResponseEntity<RespIdDto> updateBook(@PathVariable(name = "id_book_stock") long id, @RequestBody @Valid BookStockReqDto bookStockReqDto) {
        if (bookStockService.updateBookStock(bookStockReqDto, id) != null)
            return ResponseEntity.ok(new RespIdDto(id));
        log.error("Book stock could not be updated!");
        throw new BookDeleteError("Book stock could not be updated!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Operation(
            summary = "Delete book",
            description = "Delete book by id"
    )
    @DeleteMapping("/delete_book/{id_book_stock}")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "id_book_stock") long id){
        if (bookStockService.deleteBookStock(id) != null)
            return ResponseEntity.noContent().build();
        log.error("Book stock could not be deleted!");
        throw new BookDeleteError("Book stock could not be deleted!", HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
