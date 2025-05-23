package com.renigomes.api_livraria.order_package.purchase_order.DTO;

import com.renigomes.api_livraria.order_package.cupom.DTO.OrderCupomRespDto;
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
    private OrderCupomRespDto cupom;
    private BigDecimal totalValue;
}
