package com.renigomes.api_livraria.delivery_control.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.delivery_control.DTO.DeliveredRespDto;
import com.renigomes.api_livraria.delivery_control.DTO.DeliveryReqDto;
import com.renigomes.api_livraria.delivery_control.DTO.SetTransportationRespDto;
import com.renigomes.api_livraria.delivery_control.DTO.SetTransportReqDto;
import com.renigomes.api_livraria.delivery_control.service.DeliveryConService;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery_control")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@AllArgsConstructor
@Tag(name = "Delivery Control")
public class DeliveryConController {

    private final DeliveryConService deliveryConService;

    @Operation(
            summary = "Find delivery control by order id",
            method = "Method responsible for finding a delivery control by order id"
    )
    @GetMapping("/{id_order}")
    public ResponseEntity<DeliveredRespDto> findDeliveryControl(@PathVariable Long id_order) {
        return ResponseEntity.ok(deliveryConService.findDeliveryControl(id_order));
    }

    @Operation(
            summary = "Create a new delivery control",
            description = "Method responsible for creating a new delivery control"
    )
    @PostMapping
    public ResponseEntity<RespIdDto> createDeliveryControl(@RequestBody @Valid  DeliveryReqDto deliveryReqDto) {
        return ResponseEntity.ok(deliveryConService.createDeliControl(deliveryReqDto));
    }

    @Operation(
            summary = "Set transportation",
            description = "Method responsible for setting the transportation of a delivery"
    )
    @PatchMapping("/{id_order}")
    public ResponseEntity<SetTransportationRespDto> setTransportation(@PathVariable Long id_order,
                                                                      @RequestBody @Valid SetTransportReqDto setTransportReqDto) {
        return ResponseEntity.ok(deliveryConService.setTransportation(id_order, setTransportReqDto));
    }

    @Operation(
            summary = "Setting delivery as delivered",
            description = "Method responsible for setting the delivery as delivered"
    )
    @PatchMapping("/delivered/{id_order}")
    public ResponseEntity<DeliveredRespDto> setDelivered(@PathVariable Long id_order) {
        return ResponseEntity.ok(deliveryConService.setDelivered(id_order));
    }
}
