package com.renigomes.api_livraria.book.util;
import com.renigomes.api_livraria.book.dto.BookRespUserDto;
import com.renigomes.api_livraria.book.exception.OutStockException;
import com.renigomes.api_livraria.book.model.Book;
import com.renigomes.api_livraria.book_stock.DTO.BookStockRespUserDto;
import com.renigomes.api_livraria.book_stock.model.BookStock;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class Utilities {

    @Autowired
    private ModelMapper modelMapper;


    private BigDecimal calculateTotalPrice(Book book){
        return book.getPurchasePrice().add(
                book.getPurchasePrice()
                        .multiply(
                                BigDecimal
                                        .valueOf(
                                                book.getProfitMargin()
                                        )
                        )
        );
    }

    public List<BookStockRespUserDto> bookOrganizer(List<BookStock> bookStockList){
        if (!bookStockList.isEmpty()) {
            return bookStockList
                    .stream()
                    .map(bookStock -> {
                        BookRespUserDto bookRespUserDto = modelMapper.map(bookStock.getBook(), BookRespUserDto.class);
                        bookRespUserDto.setTotalPrice(calculateTotalPrice(bookStock.getBook()));
                        BookStockRespUserDto bookStockRespUserDto = modelMapper.map(bookStock, BookStockRespUserDto.class);
                        bookStockRespUserDto.setBook(bookRespUserDto);
                        return bookStockRespUserDto;
                    }).toList();
        }
        log.error("Book out of stock!");
        throw new OutStockException("Book out of stock!", HttpStatus.BAD_REQUEST);
    }

}
