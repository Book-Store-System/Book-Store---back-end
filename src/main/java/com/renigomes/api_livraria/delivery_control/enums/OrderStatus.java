package com.renigomes.api_livraria.delivery_control.enums;

public enum OrderStatus {

    PACKING("PACKING"),
    ON_THE_WAY("ON THE WAY"),
    DELIVERED("DELIVERED");

    public final String value;
    OrderStatus(String value) {
        this.value = value;
    }
}
