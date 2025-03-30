package com.renigomes.api_livraria.book.dto;

import com.renigomes.api_livraria.offer.model.Offer;
import com.renigomes.api_livraria.purchase_order.DTO.OfferOrderRespDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
