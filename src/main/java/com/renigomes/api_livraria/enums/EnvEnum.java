package com.renigomes.api_livraria.enums;

public enum EnvEnum {

    HOST_DB("HOST_DB"),
    NAME_DB("NAME_DB"),
    USER_DB("USER_DB"),
    PASSWORD_DB("PASSWORD_DB"),
    EMAIL_USER("EMAIL_USER"),
    EMAIL_PASSWORD("EMAIL_PASSWORD");

    public final String value;

    EnvEnum(String value) {
        this.value = value;
    }
}
