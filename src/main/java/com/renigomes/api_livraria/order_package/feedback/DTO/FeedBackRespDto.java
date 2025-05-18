package com.renigomes.api_livraria.order_package.feedback.DTO;

import com.renigomes.api_livraria.book_package.book.dto.BookRespFeedBackDto;
import com.renigomes.api_livraria.user_package.user.DTO.UserRespFeedBackDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedBackRespDto {
    @NotNull
    private UserRespFeedBackDto user;
    @NotNull
    private BookRespFeedBackDto book;
    @NotBlank
    private String feedback;
}
