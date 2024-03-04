package com.gridnine.testing.filters;

import com.gridnine.testing.models.Flight;

import java.util.List;

public interface Filter {
    List<Flight> doFilter(List<Flight> flights);
}
