package com.renigomes.api_livraria.order_package.cupom.enums;

public enum TypeCupom {
    PERCENTAGE("PERCENTAGE"),
    SHIPPING("SHIPPING");

    public final String value;

    TypeCupom(String value) {
        this.value = value;
    }
}
