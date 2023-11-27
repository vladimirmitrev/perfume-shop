package com.app.perfumeshop.model.enums;

public enum CourierNameEnum {
    SPEEDY("Speedy"), ECONT("Econt");

    private final String value;
    private CourierNameEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

