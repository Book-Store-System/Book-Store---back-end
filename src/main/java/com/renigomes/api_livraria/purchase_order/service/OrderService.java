package com.renigomes.api_livraria.purchase_order.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.purchase_order.DTO.ItemOrderReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Transactional
    public RespIdDto createOrder(ItemOrderReqDto itemCartReqDto) {

        return new RespIdDto(1L);
    }
}
