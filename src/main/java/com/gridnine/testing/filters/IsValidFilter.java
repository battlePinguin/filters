package com.gridnine.testing.filters;

import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;

import java.util.List;

public class IsValidFilter implements Filter {

    @Override
    public List<Flight> doFilter(List<Flight> flights) {
        return flights.stream().filter(IsValidFilter::isFlightValid).toList();
    }

    /**
     * Method to determine if flight is valid: all segments are valid and no segments overlap
     * @return true if valid
     */
    private static boolean isFlightValid(Flight flight) {

        for (Segment segment : flight.getSegments()) {
            if (!isSegmentValid(segment)) {
                return false;
            }
        }

        var segmentsSorted =FilterUtils.getSegmentsSorted(flight.getSegments());

        for (int i = 0; i < segmentsSorted.size() - 1; i++) {
            if (segmentsSorted.get(i).getArrivalDate().isAfter(segmentsSorted.get(i + 1).getDepartureDate())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to determine if segment is valid: arrival is after departure
     * @return true if valid
     */
    private static boolean isSegmentValid(Segment segment) {
        return segment.getDepartureDate().isBefore(segment.getArrivalDate());
    }
}
