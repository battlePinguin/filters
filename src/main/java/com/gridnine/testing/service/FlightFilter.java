package com.gridnine.testing.service;

import com.gridnine.testing.filters.*;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.FlightBuilder;
import com.gridnine.testing.utils.FilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class FlightFilter {

    private final String[] FILTER_NAMES = new String[]{"DEPARTURE","JETLAG","TRANSFER"};
    @Autowired
    private Map<String, Filter> filters;
    @Autowired
    private FilterUtils filterUtils;

    public void processFlights() {
        List<Flight> flights = FlightBuilder.createFlights();

        for (int i = 0; i < filters.size(); i++) {
            List<Flight> depature = filterUtils.filterFlights(flights, filters.get(FILTER_NAMES[i]));
            filterUtils.printFilteredFlights(depature, filters.get(FILTER_NAMES[i]).getType()
                + " - " + filters.get(FILTER_NAMES[i]).getTime());
        }
    }
}
