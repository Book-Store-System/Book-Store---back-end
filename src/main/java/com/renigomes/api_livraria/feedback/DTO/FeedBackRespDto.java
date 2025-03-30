package com.renigomes.api_livraria.feedback.DTO;

import com.renigomes.api_livraria.book.dto.BookRespFeedBackDto;
import com.renigomes.api_livraria.user.DTO.UserRespFeedBackDto;
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
