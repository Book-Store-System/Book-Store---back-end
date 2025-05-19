package com.renigomes.api_livraria.book_package.book.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.renigomes.api_livraria.book_package.book.component.BookComponent;
import com.renigomes.api_livraria.book_package.book.exception.NotFoundException;
import com.renigomes.api_livraria.book_package.book.exception.OutStockException;
import com.renigomes.api_livraria.book_package.book.model.Book;
import com.renigomes.api_livraria.book_package.book.model.BookStock;
import com.renigomes.api_livraria.book_package.book.repository.BookRepository;
import com.renigomes.api_livraria.book_package.book.repository.BookStockRepository;
import com.renigomes.api_livraria.user_package.user.component.UserComponent;
import com.renigomes.api_livraria.user_package.user.enums.Role;
import com.renigomes.api_livraria.user_package.user.model.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BookStockRepository bookStockRepository;
    private UserComponent userComponent;
    private BookComponent bookComponent;

    private List<?> getBookStock(List<Book> listBook, Long id_user){
        if (!listBook.isEmpty()){
            List<BookStock> listStockBook;
            User user = userComponent.extractUser(id_user);
            if (user == null){
                listStockBook = listBook
                        .stream()
                        .map(book -> bookStockRepository.findByBook(book).get())
                        .filter(bookStock -> !bookStock.isDeleted())
                        .toList();
                return bookComponent.bookOrganizerAll(listStockBook);
            }
            if(user.getRole() == Role.ADMIN)
                listStockBook = listBook
                    .stream()
                    .map(book -> bookStockRepository.findByBook(book).get())
                    .toList();
            else listStockBook = listBook
                    .stream()
                    .map(book -> bookStockRepository.findByBook(book).get())
                    .filter(bookStock -> !bookStock.isDeleted())
                    .toList();

            List<?> bookOrganizer = bookComponent.bookOrganizer(listStockBook, user);
            if (bookOrganizer != null) return bookOrganizer;
            log.error("Book out of stock!");
            throw new OutStockException("Book out of stock!", HttpStatus.BAD_REQUEST);
        }
        log.error("Book not found!");
        throw new NotFoundException("Book not found!", HttpStatus.BAD_REQUEST);
    }

    public List<?> filterBookByGenre(String category, Long id_user){
        category = '%' +  category + '%';
        List<Book> listBook = bookRepository.findByCategory_Name(category);
        return getBookStock(listBook, id_user);
    }

    public  List<?> findBook(String search, Long id_user){
        search = "%"+search+"%";
        List<Book> listBook = bookRepository.findByCategoryOrAuthorOrPublisherOrTitle(search, search, search, search);
        return getBookStock(listBook, id_user);
    }
}
