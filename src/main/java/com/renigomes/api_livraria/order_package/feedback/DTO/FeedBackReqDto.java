package com.renigomes.api_livraria.order_package.feedback.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackReqDto {
    @NotNull
    private Long bookStock_id;
    @NotBlank
    private String feedback;
}
