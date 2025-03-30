package com.renigomes.api_livraria.cupom.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.cupom.DTO.CupomReqDto;
import com.renigomes.api_livraria.cupom.exceptions.InternalErrorCupomException;
import com.renigomes.api_livraria.cupom.service.CupomService;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cupom")
@Tag(name = "Cupom")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@AllArgsConstructor
@Slf4j
public class CupomController {

    private final CupomService cupomService;

    @Operation(
            summary = "Find all cupom",
            description = "Method to find all cupom"
    )
    @GetMapping
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(cupomService.findCupomAll(request));
    }

    @Operation(
            summary = "Create a new cupom",
            description = "Method to create a new cupom"
    )
    @PostMapping
    public ResponseEntity<RespIdDto> saveCupom(@RequestBody @Valid CupomReqDto cupomReqDto) {
        return ResponseEntity.ok(cupomService.saveCupom(cupomReqDto));
    }

    @Operation(
            summary = "Alter cupom active",
            description = "Method to alter cupom active"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> setCupomActive(@PathVariable Long id) {
        if (cupomService.setCupomActive(id) != null) {
            return ResponseEntity.noContent().build();
        }
        log.error("Error to alter cupom active");
        throw  new InternalErrorCupomException("Error to alter cupom active", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
