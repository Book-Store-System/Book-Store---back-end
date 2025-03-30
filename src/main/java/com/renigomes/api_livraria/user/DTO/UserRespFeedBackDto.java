package com.renigomes.api_livraria.user.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserRespFeedBackDto {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
}
