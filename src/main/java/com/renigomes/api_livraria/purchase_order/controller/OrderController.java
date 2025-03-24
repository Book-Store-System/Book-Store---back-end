package com.renigomes.api_livraria.purchase_order.controller;

import com.renigomes.api_livraria.purchase_order.DTO.OrderReqDTO;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Order")
@RequestMapping("api/order")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class OrderController {

    @Operation(
            summary = "create order",
            description = "Method to create order"
    )
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderReqDTO orderReqDTO) {
        return ResponseEntity.ok("Order created");
    }
}
