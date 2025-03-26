package com.renigomes.api_livraria.book.controller;

import java.math.BigDecimal;
import java.util.List;

import com.renigomes.api_livraria.book.component.BookComponent;
import com.renigomes.api_livraria.book.dto.BookStockRespAdminDTO;
import com.renigomes.api_livraria.book.dto.BookStockRespUserDto;
import com.renigomes.api_livraria.book.exception.BookDeleteError;
import com.renigomes.api_livraria.book.model.BookStock;
import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book.service.BookService;
import com.renigomes.api_livraria.book.dto.BookStockReqDto;
import com.renigomes.api_livraria.book.service.BookStockService;
import com.renigomes.api_livraria.security.SecurityConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/book")
@Tag(name = "Book")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@AllArgsConstructor
public class BookController {

    private BookService bookService;
    private BookStockService bookStockService;
    private UserService userService;
    private ModelMapper modelMapper;
    private BookComponent bookComponent;

    @Operation(
            summary = "Find book by id",
            description = "Method to find book by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findByid(@PathVariable long id, HttpServletRequest request) {
        User user = userService.extractUserByToker(request);
        BookStock bookStock = bookStockService.findBookByID(id);
        BigDecimal totalPrice = bookComponent.calculateTotalPrice(bookStock);
        if (user.getRole() == Role.ADMIN){
            BookStockRespAdminDTO bookStockRespAdminDTO = modelMapper.map(bookStock, BookStockRespAdminDTO.class);
            bookStockRespAdminDTO.setSalePrice(totalPrice);
            return ResponseEntity.ok(modelMapper.map(bookStock, BookStockRespAdminDTO.class));
        }
        BookStockRespUserDto bookStockRespUserDto = modelMapper.map(bookStock, BookStockRespUserDto.class);
        bookStockRespUserDto.setSalePrice(totalPrice);
        return ResponseEntity.ok(bookStockRespUserDto);
    }

    @Operation(
            summary = "Find all book stock",
            description = "Method to find all book stock"
    )
    @GetMapping
    public ResponseEntity<List<?>> getAllBookStock(HttpServletRequest request){
        return ResponseEntity.ok(bookStockService.findAllBooks(request));
    }
    @Operation(
            summary = "Book search",
            description = "Performs search for books by author, title or genre"
    )
    @GetMapping("/search")
    public ResponseEntity<List<?>> findBySearch(@RequestParam String search, HttpServletRequest request) {
        return ResponseEntity.ok(bookService.findBook(search, request));
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
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void> activateBook(@PathVariable(name = "id") long id){
        if (bookStockService.activeBookStock(id) != null)
            return ResponseEntity.noContent().build();
        log.error("Book stock could not be activated!");
        throw new BookDeleteError("Book stock could not be deleted!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Update book",
            description = "Method to update a book by id"
    )
    @PutMapping("/{id}")
    public ResponseEntity<RespIdDto> updateBook(@PathVariable(name = "id") long id, @RequestBody @Valid BookStockReqDto bookStockReqDto) {
        if (bookStockService.updateBookStock(bookStockReqDto, id) != null)
            return ResponseEntity.ok(new RespIdDto(id));
        log.error("Book stock could not be updated!");
        throw new BookDeleteError("Book stock could not be updated!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Operation(
            summary = "Delete book",
            description = "Delete book by id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "id") long id){
        if (bookStockService.deleteBookStock(id) != null)
            return ResponseEntity.noContent().build();
        log.error("Book stock could not be deleted!");
        throw new BookDeleteError("Book stock could not be deleted!", HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
