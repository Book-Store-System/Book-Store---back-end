package com.renigomes.api_livraria.book_package.book.controller;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book_package.book.component.BookComponent;
import com.renigomes.api_livraria.book_package.book.dto.BookStockReqDto;
import com.renigomes.api_livraria.book_package.book.dto.BookStockRespAdminDTO;
import com.renigomes.api_livraria.book_package.book.dto.BookStockRespUserDto;
import com.renigomes.api_livraria.book_package.book.exception.BookDeleteError;
import com.renigomes.api_livraria.book_package.book.model.BookStock;
import com.renigomes.api_livraria.book_package.book.service.BookService;
import com.renigomes.api_livraria.book_package.book.service.BookStockService;
import com.renigomes.api_livraria.security.SecurityConfig;
import com.renigomes.api_livraria.user_package.user.component.UserComponent;
import com.renigomes.api_livraria.user_package.user.enums.Role;
import com.renigomes.api_livraria.user_package.user.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/book")
@Tag(name = "Book")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@AllArgsConstructor
public class BookController {

    private BookService bookService;
    private BookStockService bookStockService;
    private UserComponent userComponent;
    private ModelMapper modelMapper;
    private BookComponent bookComponent;

    @Operation(
        summary = "Filter book by genre",
        description = "Method to filter book by genre"
    )
    @GetMapping("/filter")
    public ResponseEntity<List<?>> filterBookByGenre(@RequestParam String genre, @RequestParam(required = false) Long id_user) {
        id_user = id_user == null ? 0L : id_user;
        return ResponseEntity.ok(bookService.filterBookByGenre(genre, id_user));
    }

    @Operation(
            summary = "Find book by id",
            description = "Method to find book by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findByid(@PathVariable long id, @RequestParam Long id_user) {
        User user = userComponent.extractUser(id_user);
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
    @GetMapping("/{id_user}")
    public ResponseEntity<List<?>> getAllBookStock(@PathVariable Long id_user) {
        return ResponseEntity.ok(bookStockService.findAllBooks(id_user));
    }
    @Operation(
            summary = "Book search",
            description = "Performs search for books by author, publisher, title or genre"
    )
    @GetMapping("/search")
    public ResponseEntity<List<?>> findBySearch(@RequestParam String search, @RequestParam(required = false) Long id_user) {
        id_user = id_user == null ? 0L : id_user;
        return ResponseEntity.ok(bookService.findBook(search, id_user));
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

    @Operation(
            summary = "Add Offer",
            description = "Method to add offer"
    )
    @PatchMapping("/add_offer/{id}")
    public ResponseEntity<BookStockRespAdminDTO> addOffer(@PathVariable(name = "id") long id_book, @RequestParam Long id_offer){
        return ResponseEntity.ok(bookStockService.addOffer(id_book, id_offer));
    }




}
