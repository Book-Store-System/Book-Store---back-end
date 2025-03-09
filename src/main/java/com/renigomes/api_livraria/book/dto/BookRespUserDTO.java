package com.renigomes.api_livraria.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookRespUserDTO {
    @NotBlank
    private  String title;
    @NotBlank
    private  String author;
    @NotBlank
    private  String picture;
}
