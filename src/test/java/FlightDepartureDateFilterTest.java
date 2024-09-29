import com.gridnine.testing.enums.DateFilterEnum;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.filters.impl.FlightDepartureDateFilter;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.FlightBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightDepartureDateFilterTest {

    private Filter<LocalDateTime> beforeFilter;
    private Filter<LocalDateTime> afterFilter;
    private Filter<LocalDateTime> todayFilter;
    private List<Flight> allFlights;

    @BeforeEach
    void setUp() {

        LocalDateTime currentTime = LocalDateTime.now();

        beforeFilter = new FlightDepartureDateFilter(currentTime, DateFilterEnum.BEFORE);
        afterFilter = new FlightDepartureDateFilter(currentTime, DateFilterEnum.AFTER);
        todayFilter = new FlightDepartureDateFilter(currentTime, DateFilterEnum.TODAY);

        allFlights = FlightBuilder.createFlights();
    }

    @Test
    void whenBeforeFilter_return1Flight() {
        // Рейс с отправлением раннее текущего 
        Flight flightBefore = allFlights.get(2); 

        List<Flight> filteredFlights = beforeFilter.doFilter(List.of(flightBefore));
        assertEquals(1, filteredFlights.size());
    }

    @Test
    void whenAfterFilter_return1Flight() {
        // Рейс с отправлением после текущего
        Flight flightAfter = allFlights.get(0);

        List<Flight> filteredFlights = afterFilter.doFilter(List.of(flightAfter));
        assertEquals(1, filteredFlights.size());
    }

    @Test
    void whenNoMatches_return0Flight() {
        // Рей, которые не совпадают с текущим временем
        Flight flightBefore = allFlights.get(2); // Рейс с отправлением 6 дней назад
        Flight flightAfter = allFlights.get(0); // Рейс с отправлением через 3 дня

        // Тестируем, что ни один из рейсов не совпадает с сегодняшней датой
        List<Flight> filteredFlights = todayFilter.doFilter(List.of(flightBefore, flightAfter));
        assertEquals(0, filteredFlights.size());
    }
}