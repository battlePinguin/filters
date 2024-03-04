package com.gridnine.testing;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.models.Flight;
import java.util.List;

public class FilterService {
    public List<Flight> FilterFlights(List<Flight> flights, Filter... filters ) {
        List<Flight> result = List.copyOf(flights);
        for (var filter : filters) {
            result = filter.doFilter(result);
        }
        return result;
    }
}
