package com.renigomes.api_livraria.book_package.book.component;

import com.renigomes.api_livraria.book_package.book.dto.BookRespDto;
import com.renigomes.api_livraria.book_package.book.dto.BookStockRespAdminDTO;
import com.renigomes.api_livraria.book_package.book.dto.BookStockRespUserDto;
import com.renigomes.api_livraria.book_package.book.model.BookStock;
import com.renigomes.api_livraria.user_package.user.enums.Role;
import com.renigomes.api_livraria.user_package.user.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class BookComponent {

    private ModelMapper modelMapper;

    public BigDecimal calculateTotalPrice(BookStock bookStock){
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
    public List<BookStockRespUserDto> bookOrganizerAll(List<BookStock> bookStockList){
        if (!bookStockList.isEmpty()) {
            return bookStockList
                            .stream()
                            .map(this::getBookStockRespUserDTO).toList();
        }
        return null;
    }

}
