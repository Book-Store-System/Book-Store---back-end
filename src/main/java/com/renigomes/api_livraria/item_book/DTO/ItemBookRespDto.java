package com.renigomes.api_livraria.item_book.DTO;

import jakarta.validation.constraints.NotNull;

public record ItemBookRespDto (
        @NotNull
         String bookTitle,
         @NotNull
         Integer quantity
        ){}
