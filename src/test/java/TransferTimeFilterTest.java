import com.gridnine.testing.enums.DurationFilterEnum;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.filters.impl.TransferTimeFilter;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.FlightBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferTimeFilterTest {

    private Filter<String> lessTransferTimeFilter;
    private Filter<String> moreTransferTimeFilter;
    private Filter<String> exactTransferTimeFilter;
    private List<Flight> flights;

    @BeforeEach
    void setUp() {
        Duration referenceDuration = Duration.ofHours(2);
        lessTransferTimeFilter = new TransferTimeFilter(referenceDuration, DurationFilterEnum.LESS);
        moreTransferTimeFilter = new TransferTimeFilter(referenceDuration, DurationFilterEnum.MORE);
        exactTransferTimeFilter = new TransferTimeFilter(referenceDuration, DurationFilterEnum.EXACT);
        flights = FlightBuilder.createFlights();
    }

    @Test
    void whenMoreThan2HoursTransferTimeFilter_Return2Flights () {
        List<Flight> filteredFlights = moreTransferTimeFilter.doFilter(flights);
        assertEquals(2, filteredFlights.size());
    }

    @Test
    void whenLessThan2HoursTransferTimeFilter_Return4Flights() {
        List<Flight> filteredFlights = lessTransferTimeFilter.doFilter(flights);
        assertEquals(4, filteredFlights.size());
    }

    @Test
    void whenExactly2HoursTransferTimeFilter_Return0Flights() {
        List<Flight> filteredFlights = exactTransferTimeFilter.doFilter(flights);
        assertEquals(0, filteredFlights.size());
    }
}
