package com.renigomes.api_livraria.order_package.purchase_order.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.order_package.cupom.DTO.CupomRespDto;
import com.renigomes.api_livraria.order_package.purchase_order.DTO.OrderReqDTO;
import com.renigomes.api_livraria.order_package.purchase_order.DTO.OrderRespDto;
import com.renigomes.api_livraria.order_package.purchase_order.service.OrderService;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
            summary = "get order by id",
            description = "Method to get order by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderRespDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Operation(
            summary = "get order",
            description = "Method to get order"
    )
    @GetMapping("/{id_user}")
    public ResponseEntity<List<OrderRespDto>> getOrder(@PathVariable Long id_user) {
        return ResponseEntity.ok(orderService.findByOrderUser(id_user));
    }

    @Operation(
            summary = "create order",
            description = "Method to create order"
    )
    @PostMapping("/{id_user}")
    public ResponseEntity<RespIdDto> createOrder(@RequestBody @Valid OrderReqDTO orderReqDTO, @PathVariable Long id_user) {
        return ResponseEntity.ok(orderService.createOrder(orderReqDTO, id_user));
    }

    @Operation(
            summary = "add cupom",
            description = "Method to add cupom in order"
    )
    @PatchMapping("/add_cupom/{id}")
    public ResponseEntity<CupomRespDto> addCupom(@PathVariable Long id, @RequestParam String codeCupom) {
        return ResponseEntity.ok(orderService.addCupom(id, codeCupom));
    }







}
