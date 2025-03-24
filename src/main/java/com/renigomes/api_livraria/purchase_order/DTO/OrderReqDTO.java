package com.renigomes.api_livraria.purchase_order.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDTO {
    @NotNull
    @NotEmpty
    private List<ItemOrderReqDto> items;
    @NotBlank
    private String payment;
    @NotNull
    private BigDecimal shipping;
}
