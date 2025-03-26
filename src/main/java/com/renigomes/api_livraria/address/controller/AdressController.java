package com.renigomes.api_livraria.address.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.address.DTO.AddressReqDto;
import com.renigomes.api_livraria.address.DTO.AddressRespDto;
import com.renigomes.api_livraria.address.exceptions.AddressException;
import com.renigomes.api_livraria.address.exceptions.AddressUpdateError;
import com.renigomes.api_livraria.address.service.AddressService;
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

import java.util.List;

import static com.renigomes.api_livraria.address.service.AddressService.ADDRESS_COULD_NOT_BE_UPDATED;

@Slf4j
@RestController
@Tag(name = "User Address")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@RequestMapping("api/user_address")
@AllArgsConstructor
public class AdressController {

    private AddressService addressService;

    private AddressException addressInternaError() {
        log.error(ADDRESS_COULD_NOT_BE_UPDATED);
        return new AddressUpdateError(ADDRESS_COULD_NOT_BE_UPDATED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

    @Operation(
            summary = "Find user address",
            description = "Method to find a address by user id"
    )
    @GetMapping
    public ResponseEntity<List<AddressRespDto>>  findUserAddress(HttpServletRequest request) {
        return ResponseEntity.ok(addressService.getAddressById(request));
    }

    @Operation(
            summary = "Create a address",
            description = "Method to create a address by user id"
    )
    @PostMapping
    public ResponseEntity<RespIdDto> createAddress(HttpServletRequest request, @RequestBody @Valid AddressReqDto address) {
        return ResponseEntity.ok(addressService.createAddress(request, address));
    }

    @Operation(
            summary = "Update user address",
            description = "Method to update a user's address"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateAddress(@PathVariable long id, @RequestBody @Valid AddressReqDto address) {
        if (addressService.updateAddress(id, address)!=null) {
            return ResponseEntity.noContent().build();
        }
        throw addressInternaError();
    }

    @PatchMapping("/{id}/is_default")
    public ResponseEntity<?> updateAddressDefault(@PathVariable long id) {
        if (addressService.updateAddressDefault(id)!=null) {
            return ResponseEntity.noContent().build();
        }
        throw addressInternaError();
    }

    @Operation(
            summary = "Delete user address",
            description = "Method to delete a user's address"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
