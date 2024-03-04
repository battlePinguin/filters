package com.gridnine.testing;

import com.gridnine.testing.enums.DateFilteringTypes;
import com.gridnine.testing.enums.DurationFilteringTypes;
import com.gridnine.testing.filters.*;
import com.gridnine.testing.models.FlightBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FlightFilter {

    public static void main(String[] args) {
        FilterService service = new FilterService();
        var flights = FlightBuilder.createFlights();

        //Фильтр на валидные полеты (сегменты не летят "в прошлое", сегменты не пересекаются)
        Filter filterValid = new IsValidFilter();
        //фильтр на полеты от текущей даты
        Filter filterFromNow = new FlightDepartureDateFilter(LocalDateTime.now(), DateFilteringTypes.AFTER);
        //Фильтр на время на земле
        Filter filterLessThan2HoursOnGround = new TransferTimeFilter(Duration.of(2, ChronoUnit.HOURS), DurationFilteringTypes.LESS);

        //Выборка полетов по вышеупомянутым фильтрам
        var resultValid = service.FilterFlights(flights, filterValid);
        System.out.println("Валидные рейсы:");
        printListLineByLine(resultValid);

        var resultFromNow = service.FilterFlights(flights, filterFromNow);
        System.out.println("Рейсы от сегодняшнего числа:");
        printListLineByLine(resultFromNow);

        var resultLessThan2HoursOnGround = service.FilterFlights(flights, filterLessThan2HoursOnGround);
        System.out.println("Рейсы с пересадкой менее двух часов:");
        printListLineByLine(resultLessThan2HoursOnGround);
    }

    private static void printListLineByLine(List list) {
        for(var e : list) {
            System.out.println(e);
        }
    }
}
