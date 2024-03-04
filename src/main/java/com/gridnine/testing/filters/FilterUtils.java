package com.gridnine.testing.filters;

import com.gridnine.testing.models.Segment;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class FilterUtils {
    public static Segment getFirstSegment (List<Segment> segments) {
        return segments.stream()
                .min(Comparator.comparing(Segment::getDepartureDate))
                .orElseThrow(NoSuchElementException::new);
    }

    public static Segment getLastSegment (List<Segment> segments) {
        return segments.stream()
                .max(Comparator.comparing(Segment::getArrivalDate))
                .orElseThrow(NoSuchElementException::new);
    }

    public static List<Segment> getSegmentsSorted(List<Segment> segments) {
        return segments.stream()
                .sorted(Comparator.comparing(Segment::getDepartureDate))
                .toList();
    }
}
