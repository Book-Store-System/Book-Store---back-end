package com.renigomes.api_livraria.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;
}
