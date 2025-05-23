package com.renigomes.api_livraria.book_package.book.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book_package.author.model.Author;
import com.renigomes.api_livraria.book_package.author.service.AuthorService;
import com.renigomes.api_livraria.book_package.book.component.BookComponent;
import com.renigomes.api_livraria.book_package.book.dto.BookRespDto;
import com.renigomes.api_livraria.book_package.book.dto.BookStockRespAdminDTO;
import com.renigomes.api_livraria.book_package.book.exception.BookOfferInative;
import com.renigomes.api_livraria.book_package.book.exception.NotFoundException;
import com.renigomes.api_livraria.book_package.book.model.Book;
import com.renigomes.api_livraria.book_package.book.repository.BookRepository;
import com.renigomes.api_livraria.book_package.book.dto.BookStockReqDto;
import com.renigomes.api_livraria.book_package.book.exception.UniqueTitleError;
import com.renigomes.api_livraria.book_package.book.model.BookStock;
import com.renigomes.api_livraria.book_package.book.repository.BookStockRepository;
import com.renigomes.api_livraria.book_package.category.model.Category;
import com.renigomes.api_livraria.book_package.category.service.CategoryService;
import com.renigomes.api_livraria.book_package.offer.model.Offer;
import com.renigomes.api_livraria.book_package.offer.service.OfferService;
import com.renigomes.api_livraria.book_package.publisher.model.Publisher;
import com.renigomes.api_livraria.book_package.publisher.service.PublisherService;
import com.renigomes.api_livraria.user_package.user.component.UserComponent;
import com.renigomes.api_livraria.user_package.user.enums.Role;
import com.renigomes.api_livraria.user_package.user.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BookStockService {

    private static final String BOOK_OUT_OF_STOCK = "Book out of stock!";
    private CategoryService categoryService;
    private BookComponent bookComponent;
    private ModelMapper modelMapper;
    private BookRepository bookRepository;
    private BookStockRepository bookStockRepository;
    private UserComponent userComponent;
    private OfferService offerService;
    private AuthorService authorService;
    private PublisherService publisherService;

    private static NotFoundException get() {
        log.error(BOOK_OUT_OF_STOCK);
        return new NotFoundException(BOOK_OUT_OF_STOCK, HttpStatus.NOT_FOUND);
    }

    public  BookStock findBookByID(long id){
        Optional<BookStock> bookStockOptional = bookStockRepository.findById(id);
        if (bookStockOptional.isPresent()) {
            return bookStockOptional.get();
        }
        log.error("Book not found!");
        throw new NotFoundException("Book not found!", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public RespIdDto save(BookStockReqDto bookStockReqDto){
        Book book = modelMapper.map(bookStockReqDto.getBook(), Book.class);
        if (bookRepository.findByTitle(book.getTitle()).isPresent())
            throw new UniqueTitleError("Title already exists", HttpStatus.CONFLICT);

        book.setRegisteredOn(LocalDate.now());
        Author author = authorService.save(new Author(bookStockReqDto.getBook().getAuthor()));
        book.setAuthor(author);
        Category category = categoryService.save(new Category(bookStockReqDto.getBook().getCategory()));
        book.setCategory(category);
        Publisher publisher = publisherService.save(new Publisher(bookStockReqDto.getBook().getPublisher()));
        book.setPublisher(publisher);
        book =  bookRepository.save(book);
        BookStock bookStock = modelMapper.map(bookStockReqDto, BookStock.class);
        bookStock.setBook(book);
        return
          new RespIdDto(
                  bookStockRepository.save(bookStock).getId()
          );
    }

    public List<?> findAllBooks(Long id_user){
        List<BookStock> books;
        User user = userComponent.extractUser(id_user);
        if (user == null)
            return bookComponent.bookOrganizerAll(bookStockRepository.findAll());
        user = userComponent.extractUser(id_user);
        if (user.getRole() == Role.ADMIN) books = bookStockRepository.findAll();
        else books = bookStockRepository.findAll()
                .stream()
                .filter(i -> !i.isDeleted())
                .toList();;
        List<?> bookOrganizer = bookComponent.bookOrganizer(books, user);
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

    @Transactional
    public BookStockRespAdminDTO addOffer(Long offerId, long id_book_stock) {
        Offer offer = offerService.findById(offerId);
        BookStock bookStock = bookStockRepository.findById(id_book_stock).orElseThrow(
                BookStockService::get
        );
        if (!offer.getActive())
            throw new BookOfferInative("Offer not active!", HttpStatus.CONFLICT);
        bookStock.setOffer(offer);
        bookStock = bookStockRepository.save(bookStock);
        BookStockRespAdminDTO bookStockRespAdminDTO = modelMapper.map(bookStock, BookStockRespAdminDTO.class);
        bookStockRespAdminDTO.setBook(modelMapper.map(bookStock.getBook(), BookRespDto.class));
        return bookStockRespAdminDTO;
    }
}
