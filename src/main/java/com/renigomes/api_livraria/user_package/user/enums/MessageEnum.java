package com.renigomes.api_livraria.user_package.user.enums;

public enum MessageEnum {

    SUBJECT_MESSAGE("Password Recovery: Don't answer that email"),
    BODY_MESSAGE("Password recovery code: ");

    public final String message;

    MessageEnum(String message) {
        this.message = message;
    }
}
