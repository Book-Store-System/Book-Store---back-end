package com.renigomes.api_livraria.purchase_order.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class OrderRespDto {
    private Long id;
    private List<ItemOrderRespDto> items;
    private BigDecimal shipping;
    private LocalDate orderDate;
    private String payment;
    private BigDecimal totalValue;
}
