import com.gridnine.testing.enums.JetlagFilterEnum;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.filters.impl.JetlagFilter;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.FlightBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


class JetlagFilterTest {

    private Filter<String> yesJetlagFilter;
    private Filter<String> noJetlagFilter;
    private List<Flight> allFlights;

    @BeforeEach
    void setUp() {
        yesJetlagFilter = new JetlagFilter(JetlagFilterEnum.YES);
        noJetlagFilter = new JetlagFilter(JetlagFilterEnum.NO);

        allFlights = FlightBuilder.createFlights();
    }

    @Test
    void whenYesJetlagFilter_Return1Flight() {
        // рейс с "джетлагом"
        Flight jetlagFlight = allFlights.get(3);

        List<Flight> filteredFlights = yesJetlagFilter.doFilter(List.of(jetlagFlight));
        assertEquals(1, filteredFlights.size());
    }

    @Test
    void whenJetlagSegmentsWithNoJetlagFilter_Return0Flights() {
        Flight jetlagFlight = allFlights.get(3);

        List<Flight> filteredFlights = noJetlagFilter.doFilter(List.of(jetlagFlight));
        assertEquals(0, filteredFlights.size());
    }

    @Test
    void whenNoJetlagFilter_Return1Flight() {
        // рейс без "джетлага"
        Flight normalFlight = allFlights.get(0);

        List<Flight> filteredFlights = noJetlagFilter.doFilter(List.of(normalFlight));
        assertEquals(1, filteredFlights.size());
    }

    @Test
    void whenMixedSegmentsWithNoJetlagFilter_Return1Flight() {
        // рейс с несколькими сегментами
        Flight mixedFlight = allFlights.get(1);

        List<Flight> filteredFlights = noJetlagFilter.doFilter(List.of(mixedFlight));
        assertEquals(1, filteredFlights.size());
    }

    @Test
    void whenMixedSegmentsWithYesJetlagFilter_Return0Flights() {
        // тот же рейс, что и в предыдущем тесте,
        Flight mixedFlight = allFlights.get(1);

        List<Flight> filteredFlights = yesJetlagFilter.doFilter(List.of(mixedFlight));
        assertEquals(0, filteredFlights.size());
    }
}