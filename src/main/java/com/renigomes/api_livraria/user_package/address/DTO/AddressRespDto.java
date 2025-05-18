package com.renigomes.api_livraria.user_package.address.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AddressRespDto {
    @NotNull
    private Long id;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String neighborhood;
    @NotBlank
    private String CEP;
    @NotNull
    private Integer number;
    @NotBlank
    private String reference;
    @NotNull
    private Boolean addressDefault;
}
