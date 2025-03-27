package com.renigomes.api_livraria.purchase_order.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.purchase_order.DTO.ItemOrderReqDto;
import com.renigomes.api_livraria.purchase_order.DTO.OrderReqDTO;
import com.renigomes.api_livraria.purchase_order.exceptions.ItemOrderException;
import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.purchase_order.repository.PurOrderRepository;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final ModelMapper modelMapper;
    private PurOrderRepository purOrderRepository;
    private UserService userService;
    private ItemOrderService itemOrderService;

    @Transactional
    public RespIdDto createOrder(OrderReqDTO orderReqDTO, HttpServletRequest request) {
        User user = userService.extractUserByToker(request);
        PurchaseOrder purOrder = modelMapper.map(orderReqDTO, PurchaseOrder.class);
        purOrder.setUser(user);
        purOrder.setOrderDate(LocalDate.now());
        purOrder = purOrderRepository.save(purOrder);
        PurchaseOrder finalPurOrder = purOrder;
        orderReqDTO.getItems()
                .forEach(item -> {
                    if (itemOrderService.createItemOrder(item, finalPurOrder)==null){
                        log.error("Item Order could not be created");
                        throw new ItemOrderException("Internal error creating item order", HttpStatus.INTERNAL_SERVER_ERROR);
                    };
                });
        return new RespIdDto(finalPurOrder.getId());
    }
}
