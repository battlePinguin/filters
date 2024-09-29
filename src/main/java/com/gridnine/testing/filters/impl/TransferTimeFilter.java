package com.gridnine.testing.filters.impl;

import com.gridnine.testing.enums.DurationFilterEnum;
import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.models.Flight;
import com.gridnine.testing.utils.FilterUtils;
import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Фильтр для рейсов на основе времени пересадки.
 */
public class TransferTimeFilter implements Filter<String> {

    private final Duration duration;
    private final DurationFilterEnum type;
    private final FilterUtils filterUtils;

    public TransferTimeFilter(Duration duration, DurationFilterEnum type) {
        this.duration = duration;
        this.type = type;
        this.filterUtils = new FilterUtils();
    }

    @Override
    public List<Flight> doFilter(List<Flight> flights) {
        Predicate<Flight> predicate = switch (type) {
            case LESS -> f -> filterUtils.getTotalTransferTime(f.getSegments()).compareTo(duration) < 0;
            case MORE -> f -> filterUtils.getTotalTransferTime(f.getSegments()).compareTo(duration) > 0;
            case EXACT -> f ->filterUtils.getTotalTransferTime(f.getSegments()).compareTo(duration) == 0;
        };

        return flights.stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    public DurationFilterEnum getType() {
        return type;
    }

    @Override
    public String getTime() {
        return duration.toString() + """
            \n    Формат строкового представления объекта Duration использует стандарт ISO-8601. Он выглядит, например, как:
                PT: Префикс для обозначения временного периода (Period of Time).
                3H: Указывает, что период составляет 3 часа (H — это аббревиатура для Hours).
            """;
    }
}
