package com.renigomes.api_livraria.book_package.book.dto;

import com.renigomes.api_livraria.order_package.purchase_order.DTO.OfferOrderRespDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookRespOrderDto {

    @NotBlank
    private  String title;
    @NotBlank
    private  String picture;
    private BigDecimal price;
    private OfferOrderRespDto offerOrderRespDto;
    
}
