package com.app.perfumeshop.model.enums;

public enum SizeEnum {
    FIFTY("50 ml"), HUNDRED("100 ml"), HUNDRED_AND_FIFTY("150 ml"), TWO_HUNDRED("200 ml");

    private final String value;

    private SizeEnum(String value) {
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
