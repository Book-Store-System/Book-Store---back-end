package com.renigomes.api_livraria.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



}
