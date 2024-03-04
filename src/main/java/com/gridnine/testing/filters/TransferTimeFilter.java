package com.gridnine.testing.filters;

import com.gridnine.testing.enums.DurationFilteringTypes;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.models.Segment;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransferTimeFilter implements Filter{
    private final Duration duration;
    private final DurationFilteringTypes type;

    public TransferTimeFilter(Duration duration, DurationFilteringTypes type) {
        this.duration = duration;
        this.type = type;
    }

    @Override
    public List<Flight> doFilter(List<Flight> flights) {
        //добавляем список полетов и в результате возвращается список отфильтрованный
        //в зависимости от того сколько сегментов было в каждом полете, есть 4 варианта
        switch (type) {
            case LESS -> {
                return flights.stream().filter(f -> compareTransferTimeWithTarget(f.getSegments()) <= 0).toList();
            }
            case MORE -> {
                return flights.stream().filter(f -> compareTransferTimeWithTarget(f.getSegments()) >= 0).toList();
            }
            case EXACT -> {
                return flights.stream().filter(f -> compareTransferTimeWithTarget(f.getSegments()) == 0).collect(Collectors.toList());
            }
            default -> {
                return new ArrayList<Flight>();
            }
        }
    }

    private int compareTransferTimeWithTarget(List<Segment> segments) { //
        if(segments.size() == 1) return -1; // если один сегмент - возвращаем
        var sortedSegments = FilterUtils.getSegmentsSorted(segments); // сортируем сегменты
        Duration timeInTransfer = Duration.of(0, ChronoUnit.HOURS);// какой то непонятных дурейшен, типо инициализация
        for(int i = 0; i < sortedSegments.size() - 1; i++) { //  цикл по сегментам, плюсуем в тайм трансфер и сравниваем с дурейшн который при инициализации метода запускается
            timeInTransfer = timeInTransfer.plus(Duration.between(
                    sortedSegments.get(i).getArrivalDate(),
                    sortedSegments.get(i + 1).getDepartureDate())
            );
        }
        return timeInTransfer.compareTo(duration);
    }
}
