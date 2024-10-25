package com.app.perfumeshop.model.enums;

public enum CategoryNameEnum {
    MEN("Men"), WOMEN("Women"), UNISEX("Unisex");

    private final String value;

    private CategoryNameEnum(String value) {
        this.value = value;
    }
    public String  getValue() {

        return value;
    }

//    @Override
//    public String toString() {
//        return value;
//    }
}
