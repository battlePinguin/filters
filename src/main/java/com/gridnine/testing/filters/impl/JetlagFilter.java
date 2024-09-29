package com.gridnine.testing.filters.impl;

import com.gridnine.testing.enums.JetlagFilterEnum;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.models.Flight;

import java.util.List;
import java.util.function.Predicate;

/**
 * Класс для фильтрации списка рейсов по параметру
 * "джетлаг" - время вылета позже времени прибытия из-за смены часовых поясов.
 *
 * @author Azamat Vladislav
 */
public class JetlagFilter implements Filter<String> {
    private final JetlagFilterEnum type;

    public JetlagFilter(JetlagFilterEnum type) {
        this.type = type;
    }

    @Override
    public List<Flight> doFilter(List<Flight> flights) {

        Predicate<Flight> predicate = switch (type) {
            case YES -> f -> f.getSegments().stream().allMatch(segment -> segment.getDepartureDate().isAfter(segment.getArrivalDate()));
            case NO -> f -> f.getSegments().stream().allMatch(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate()));
        };

        return flights.stream()
            .filter(predicate)
            .toList();
    }

    public JetlagFilterEnum getType() {
        return type;
    }

    @Override
    public String getTime() {
        return "JetlagFilterEnum";
    }
}
