package com.renigomes.api_livraria.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookRespFeedBackDto {
    @NotBlank
    private  String title;
    @NotBlank
    private  String author;
    @NotBlank
    private  String description;
}
