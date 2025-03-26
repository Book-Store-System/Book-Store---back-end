package com.renigomes.api_livraria.purchase_order.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemOrderReqDto {
    @NotNull
    private Long bookStockId;
    @NotNull
    private  Integer quantity;
}
