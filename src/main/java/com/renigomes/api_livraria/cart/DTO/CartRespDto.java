package com.renigomes.api_livraria.cart.DTO;

import com.renigomes.api_livraria.user.DTO.UserRespDtoTwo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.renigomes.api_livraria.cart.model.Cart}
 */
@AllArgsConstructor
@Getter
public class CartRespDto implements Serializable {
    @NotNull
    private final UserRespDtoTwo user;
    @NotNull
    private final BigDecimal subtotal;
}