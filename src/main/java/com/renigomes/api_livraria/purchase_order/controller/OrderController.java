package com.renigomes.api_livraria.purchase_order.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.purchase_order.DTO.OrderReqDTO;
import com.renigomes.api_livraria.purchase_order.service.OrderService;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Order")
@RequestMapping("api/order")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@AllArgsConstructor
public class OrderController {

    private  final OrderService orderService;

    @Operation(
            summary = "create order",
            description = "Method to create order"
    )
    @PostMapping
    public ResponseEntity<RespIdDto> createOrder(@RequestBody @Valid OrderReqDTO orderReqDTO, HttpServletRequest request) {
        return ResponseEntity.ok(orderService.createOrder(orderReqDTO, request));
    }




}
