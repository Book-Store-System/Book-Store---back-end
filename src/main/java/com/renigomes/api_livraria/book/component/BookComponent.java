package com.renigomes.api_livraria.book.component;

import com.renigomes.api_livraria.book.dto.BookRespDto;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespAdminDTO;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BookComponent {

    @Autowired
    private ModelMapper modelMapper;


    private BigDecimal calculateTotalPrice(BookStock bookStock){
        return bookStock.getPurchasePrice().add(
                bookStock.getPurchasePrice()
                        .multiply(
                                BigDecimal
                                        .valueOf(
                                                bookStock.getProfitMargin()
                                        )
                        )
        );
    }

    private BookStockRespAdminDTO getBookStockRespAdminDTO(BookStock bookStock){
        BookRespDto bookRespDto = modelMapper.map(bookStock.getBook(), BookRespDto.class);
        BookStockRespAdminDTO bookStockRespAdminDTO = modelMapper.map(bookStock, BookStockRespAdminDTO.class);
        bookStockRespAdminDTO.setBook(bookRespDto);
        bookStockRespAdminDTO.setSalePrice(calculateTotalPrice(bookStock));
        return bookStockRespAdminDTO;
    }

    private BookStockRespUserDto getBookStockRespUserDTO(BookStock bookStock){
        BookRespDto bookRespDto = modelMapper.map(bookStock.getBook(), BookRespDto.class);
        BookStockRespUserDto bookStockRespUserDto = modelMapper.map(bookStock, BookStockRespUserDto.class);
        bookStockRespUserDto.setBook(bookRespDto);
        bookStockRespUserDto.setSalePrice(calculateTotalPrice(bookStock));
        return bookStockRespUserDto;
    }

    public List<?> bookOrganizer(List<BookStock> bookStockList, User user){
        if (!bookStockList.isEmpty()) {
            return user.getRole() == Role.ADMIN ? bookStockList
                    .stream()
                    .map(this::getBookStockRespAdminDTO).toList():
                    bookStockList
                            .stream()
                            .map(this::getBookStockRespUserDTO).toList();
        }
        return null;
    }

}
