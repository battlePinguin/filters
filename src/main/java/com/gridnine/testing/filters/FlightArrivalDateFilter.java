package com.gridnine.testing.filters;

import com.gridnine.testing.enums.DateFilteringTypes;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FlightArrivalDateFilter implements Filter {

    private final LocalDateTime time;
    private final DateFilteringTypes type;

    public FlightArrivalDateFilter(LocalDateTime time, DateFilteringTypes type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public List<Flight> doFilter(List<Flight> flights) {
        switch (type) {
            case BEFORE -> {
                return flights.stream().filter(f -> isArrivingBeforeTime(f.getSegments())).toList();
            }
            case AFTER -> {
                return flights.stream().filter(f -> isArrivingAfterTime(f.getSegments())).toList();
            }
            case EXACT -> {
                return flights.stream().filter(f -> isArrivingAtDay(f.getSegments())).toList();
            }
            default -> {
                return new ArrayList<Flight>();
            }
        }
    }

    private boolean isArrivingBeforeTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getArrivalDate().isBefore(time));
    }

    private boolean isArrivingAfterTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getArrivalDate().isAfter(time));
    }

    private boolean isArrivingAtDay(List<Segment> segments) {
        var lastSegment = FilterUtils.getLastSegment(segments);
        return lastSegment.getArrivalDate().truncatedTo(ChronoUnit.DAYS)
            .isEqual(time.truncatedTo(ChronoUnit.DAYS));
    }
}
