package com.gridnine.testing.enums;

/**
 * Перечисление типов фильтрации дат.
 *
 * @author Азамат Владислав
 */
public enum DateFilterEnum {

    BEFORE("Рейсы с датой отправки ранее даты указанной в файле конфигурации фильтра"),
    AFTER("Рейсы с датой отправки позже даты указанной в файле конфигура фильтра"),
    TODAY("Рейсы с датой отправки сегодня");

    private final String displayName;

    DateFilterEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}