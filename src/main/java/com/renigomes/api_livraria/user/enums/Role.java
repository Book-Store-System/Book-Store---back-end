package com.renigomes.api_livraria.user.enums;

public enum Role {
    ADMIN("ADMIN"),
    CLIENT("CLIENT");
    public final String value;

    Role(String value) {
        this.value = value;
    }
}
