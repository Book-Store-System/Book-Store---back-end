package com.renigomes.api_livraria.order_package.delivery_control.enums;

public enum OrderStatus {

    PREPARING("PREPARING"),
    ON_THE_WAY("ON THE WAY"),
    DELIVERED("DELIVERED");

    public final String value;
    OrderStatus(String value) {
        this.value = value;
    }
}
