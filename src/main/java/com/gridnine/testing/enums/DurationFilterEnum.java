package com.gridnine.testing.enums;

/**
 * Перечисление типов фильтрации по суммарной длительности пересадок.
 *
 * @author Азамат Владислав
 */
public enum DurationFilterEnum {

    MORE("Рейсы с суммарным временем пересадок более указанного в параметре фильтра"),
    LESS("Рейсы с суммарным временем пересадок менее указанного в параметре фильтра"),
    EXACT("Рейсы с суммарным временем пересадок равным указанному в параметре фильтра");

    private final String displayEnum;

    DurationFilterEnum(String displayEnum) {
        this.displayEnum = displayEnum;
    }

    @Override
    public String toString() {
        return displayEnum;
    }
}