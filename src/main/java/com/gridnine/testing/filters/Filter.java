package com.gridnine.testing.filters;

import com.gridnine.testing.models.Flight;
import java.util.List;

/**
 * Интерфейс для фильтрации списка рейсов.
 *
 * @author Azamat Vladislav
 */
public interface Filter<T> {

    /**
     * Фильтрует список рейсов на основе определенных критериев.
     *
     * @param flights список рейсов для фильтрации
     * @return отфильтрованный список рейсов
     */
    List<Flight> doFilter(List<Flight> flights);

    Enum<?> getType();

    T getTime();
}