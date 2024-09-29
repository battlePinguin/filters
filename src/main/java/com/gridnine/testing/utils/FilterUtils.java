package com.gridnine.testing.utils;

import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Утилитарный класс для фильтрации и сортировки сегментов и рейсов.
 * Этот класс предоставляет методы для фильтрации списка рейсов на основе одного или нескольких фильтров,
 * печати отфильтрованных рейсов с описанием, получения первого сегмента из списка на основе даты отправления
 * и возврата отсортированного списка сегментов на основе даты отправления.
 */
@Component
public class FilterUtils {

    /**
     * Фильтрует список рейсов на основе одного или нескольких фильтров.
     *
     * @param flights список рейсов для фильтрации
     * @param filters массив фильтров, которые будут применены к списку рейсов
     * @return отфильтрованный список рейсов
     * @throws IllegalArgumentException если список рейсов или массив фильтров равен null
     * @throws IllegalStateException если один из фильтров вернул null
     */
    public List<Flight> filterFlights(List<Flight> flights, Filter... filters ) {

        if (flights == null || filters == null) {
            throw new IllegalArgumentException("Список рейсов и массив фильтров не могут быть null");
        }

        List<Flight> filteredFlights = new ArrayList<>(flights);

        for (Filter filter : filters) {
            if (filter == null) {
                continue;
            }

            List<Flight> tempFlights = filter.doFilter(filteredFlights);

            if (tempFlights == null) {
                throw new IllegalStateException("Фильтр вернул null. Это недопустимо.");
            }

            filteredFlights = tempFlights;
        }
        return filteredFlights;
    }

    /**
     * Печать отфильтрованных рейсов с описанием.
     *
     * @param flightList список отфильтрованных рейсов
     * @param filterDescription описание примененных фильтров
     */
    public void printFilteredFlights(List<Flight> flightList, String filterDescription) {
        System.out.println("\nХарактеристика примененного фильтра и параметр: " + filterDescription);
        if (flightList.isEmpty()) {
            System.out.println("Нет рейсов, соответствующих фильтру.");
        } else {
            flightList.forEach(System.out::println);
        }
    }

    /**
     * Рассчитывает общее время всех пересадок в списке сегментов.
     *
     * @param segments список сегментов рейса
     * @return общее время пересадки (Duration)
     */
    public Duration getTotalTransferTime(List<Segment> segments) {
        if (segments.size() <= 1) return Duration.ZERO;

        return getSegmentsSorted(segments).stream()
            .limit(segments.size() - 1)
            .map(segment -> Duration.between(
                segment.getArrivalDate(),
                segments.get(segments.indexOf(segment) + 1).getDepartureDate()))
            .reduce(Duration.ZERO, Duration::plus);
    }

    /**
     * Возвращает первый сегмент из списка на основе даты отправления.
     *
     * @param segments список сегментов для фильтрации
     * @return первый сегмент на основе даты отправления
     * @throws NoSuchElementException если список пуст
     */
    public Segment getFirstSegment(List<Segment> segments) {
        return segments.stream()
            .min(Comparator.comparing(Segment::getDepartureDate))
            .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Возвращает отсортированный список сегментов на основе даты отправления.
     *
     * @param segments список сегментов для сортировки
     * @return отсортированный список сегментов на основе даты отправления
     */
    public List<Segment> getSegmentsSorted(List<Segment> segments) {
        return segments.stream()
            .sorted(Comparator.comparing(Segment::getDepartureDate))
            .collect(Collectors.toList());
    }

    /**
     * Метод для определения валидности рейса: все сегменты не пересекаются.
     *
     * @param flights список рейсов для проверки
     * @return true, если рейс валиден
     */
    private static boolean isFlightValid(List<Flight> flights) {
        return flights.stream()
            .allMatch(flight -> flight.getSegments().stream()
                .sorted((s1, s2) -> s1.getDepartureDate().compareTo(s2.getDepartureDate()))
                .reduce((prev, next) -> next.getDepartureDate().isAfter(prev.getArrivalDate()) ? next : null)
                .isPresent());
    }
}
