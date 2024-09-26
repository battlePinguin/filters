package com.gridnine.testing.filters;

import com.gridnine.testing.enums.DateFilteringTypes;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FlightDepartureDateFilter implements Filter {

    private final LocalDateTime time;
    private final DateFilteringTypes type;

    public FlightDepartureDateFilter(LocalDateTime time, DateFilteringTypes type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public List<Flight> doFilter(List<Flight> flights) {
        switch (type) {
            case BEFORE -> {
                return flights.stream().filter(f -> isDepartingBeforeTime(f.getSegments())).toList();
            }
            case AFTER -> {
                return flights.stream().filter(f -> isDepartingAfterTime(f.getSegments())).toList();
            }
            case EXACT -> {
                return flights.stream().filter(f -> isDepartingAtDay(f.getSegments())).toList();
            }
            default -> {
                return new ArrayList<Flight>();
            }
        }
    }

    private boolean isDepartingBeforeTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getDepartureDate().isBefore(time));
    }

    private boolean isDepartingAfterTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getDepartureDate().isAfter(time));
    }

    private boolean isDepartingAtDay(List<Segment> segments) {
        var firstSegment = FilterUtils.getFirstSegment(segments);

        return firstSegment.getDepartureDate().truncatedTo(ChronoUnit.DAYS)
            .isEqual(time.truncatedTo(ChronoUnit.DAYS));
    }
}
