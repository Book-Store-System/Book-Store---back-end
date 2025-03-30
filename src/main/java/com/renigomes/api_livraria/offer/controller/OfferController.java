package com.renigomes.api_livraria.offer.controller;

import com.renigomes.api_livraria.offer.DTO.OfferReqDto;
import com.renigomes.api_livraria.offer.DTO.OfferRespDto;
import com.renigomes.api_livraria.offer.service.OfferService;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/offer")
@Tag(name = "Offer")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(
            summary = "Find all offers",
            description = "Method to find all offers"
    )
    @GetMapping
    public ResponseEntity<List<OfferRespDto>> findAll(){
        return ResponseEntity.ok(offerService.findAll());
    }

    @Operation(
            summary = "Create new offer",
            description = "Method to create a new offer"
    )
    @PostMapping
    public ResponseEntity<OfferRespDto> create(@Valid @RequestBody OfferReqDto offerReqDto){
        return ResponseEntity.ok(offerService.save(offerReqDto));
    }

    @Operation(
            summary = "Setting active offer",
            description = "Method to setting active offer"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<OfferRespDto> setActive(@PathVariable Long id){
        return ResponseEntity.ok(offerService.setActive(id));
    }


}
