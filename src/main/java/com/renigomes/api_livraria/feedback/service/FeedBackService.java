package com.renigomes.api_livraria.feedback.service;

import com.renigomes.api_livraria.book.dto.BookRespFeedBackDto;
import com.renigomes.api_livraria.book.service.BookStockService;
import com.renigomes.api_livraria.feedback.DTO.FeedBackReqDto;
import com.renigomes.api_livraria.feedback.DTO.FeedBackRespDto;
import com.renigomes.api_livraria.feedback.model.FeedBack;
import com.renigomes.api_livraria.feedback.repository.FeedBackRepository;
import com.renigomes.api_livraria.user.DTO.UserRespFeedBackDto;
import com.renigomes.api_livraria.user.component.UserComponent;
import com.renigomes.api_livraria.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedBackService {

    private final FeedBackRepository feedBackRepository;
    private final BookStockService bookStockService;
    private final ModelMapper modelMapper;
    private final UserComponent userComponent;

    @Transactional
    public FeedBackRespDto createFeedback(FeedBackReqDto feedBackReqDto, HttpServletRequest request) {
        FeedBack feedBack = modelMapper.map(feedBackReqDto, FeedBack.class);
        feedBack.setUser(userComponent.extractUserByToker(request));
        feedBack.setBookStock(bookStockService.findBookByID(feedBackReqDto.getBookStock_id()));
        feedBack = feedBackRepository.save(feedBack);
        FeedBackRespDto feedBackRespDto = modelMapper.map(feedBack, FeedBackRespDto.class);
        feedBackRespDto.setUser(modelMapper.map(feedBack.getUser(), UserRespFeedBackDto.class));
        feedBackRespDto.setBook(modelMapper.map(feedBack.getBookStock().getBook(), BookRespFeedBackDto.class));
        return feedBackRespDto;
    }

    public List<FeedBackRespDto> findAllByBook(Long bookStockId) {
        return feedBackRepository.findByBookStock(bookStockService.findBookByID(bookStockId))
                .stream().map(feedback -> {
                    FeedBackRespDto feedBackRespDto = modelMapper.map(feedback, FeedBackRespDto.class);
                    feedBackRespDto.setUser(modelMapper.map(feedback.getUser(), UserRespFeedBackDto.class));
                    feedBackRespDto.setBook(modelMapper.map(feedback.getBookStock().getBook(), BookRespFeedBackDto.class));
                    return feedBackRespDto;
                }).toList();
    }
}
