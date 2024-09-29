package com.gridnine.testing.filters.impl;

import com.gridnine.testing.enums.DateFilterEnum;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.utils.FilterUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

/**
 * Фильтр для рейсов на основе даты и времени отправления.
 *
 * Этот класс реализует интерфейс {@link Filter} и предоставляет методы для фильтрации рейсов
 * на основе заданного времени и типа фильтрации.
 *
 * @author Азамат Владислав
 */
public class FlightDepartureDateFilter implements Filter<LocalDateTime> {

    private final LocalDateTime time;

    private final DateFilterEnum type;

    private final FilterUtils filterUtils;
    public FlightDepartureDateFilter(LocalDateTime time, DateFilterEnum type) {
        this.time = time;
        this.type = type;
        this.filterUtils = new FilterUtils();
    }

    @Override
    public List<Flight> doFilter(List<Flight> flights) {

        Predicate<Flight> predicate = switch (type) {
            case BEFORE -> f -> f.getSegments().stream().anyMatch(s -> s.getDepartureDate().isBefore(time));
            case AFTER -> f -> f.getSegments().stream().allMatch(s -> s.getDepartureDate().isAfter(time));
            case TODAY -> f -> filterUtils.getFirstSegment(f.getSegments())
                .getDepartureDate().truncatedTo(ChronoUnit.DAYS)
                .isEqual(time.truncatedTo(ChronoUnit.DAYS));
        };

        return flights.stream()
            .filter(predicate)
            .toList();
    }

    public DateFilterEnum getType() {
        return type;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }
}
