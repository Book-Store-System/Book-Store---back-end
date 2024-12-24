package com.renigomes.api_livraria.book.util;
import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.renigomes.api_livraria.book.dto.BookRespDto;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.model.BookStock;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Utilities {

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

    public List<BookStockRespUserDto> bookOrganizer(List<BookStock> bookStockList){
        if (!bookStockList.isEmpty()) {
            return bookStockList
                    .stream()
                    .map(bookStock -> {
                        BookRespDto bookRespDto = modelMapper.map(bookStock.getBook(), BookRespDto.class);
                        BookStockRespUserDto bookStockRespUserDto = modelMapper.map(bookStock, BookStockRespUserDto.class);
                        bookStockRespUserDto.setBook(bookRespDto);
                        bookStockRespUserDto.setSalePrice(calculateTotalPrice(bookStock));
                        return bookStockRespUserDto;
                    }).toList();
        }
        return null;
    }

}
