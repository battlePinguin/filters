package com.gridnine.testing.enums;

public enum JetlagFilterEnum {

    YES("Рейсы с джетлагом"),
    NO("Рейсы без джетлага");

    private final String displayEnum;

    JetlagFilterEnum(String displayEnum) {
        this.displayEnum = displayEnum;
    }

    @Override
    public String toString() {
        return displayEnum;
    }

}
