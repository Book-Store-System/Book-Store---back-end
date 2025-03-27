package com.renigomes.api_livraria.purchase_order.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.purchase_order.DTO.OrderReqDTO;
import com.renigomes.api_livraria.purchase_order.DTO.OrderRespDto;
import com.renigomes.api_livraria.purchase_order.service.OrderService;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(
            summary = "get order",
            description = "Method to get order"
    )
    @GetMapping
    public ResponseEntity<List<OrderRespDto>> getOrder(HttpServletRequest request) {
        return ResponseEntity.ok(orderService.findByOrderUser(request));
    }

    @Operation(
            summary = "get order by id",
            description = "Method to get order by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderRespDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }




}
