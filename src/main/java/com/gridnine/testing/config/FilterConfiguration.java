package com.gridnine.testing.config;

import com.gridnine.testing.enums.DateFilterEnum;
import com.gridnine.testing.enums.DurationFilterEnum;
import com.gridnine.testing.enums.JetlagFilterEnum;
import com.gridnine.testing.filters.impl.FlightDepartureDateFilter;
import com.gridnine.testing.filters.impl.JetlagFilter;
import com.gridnine.testing.filters.impl.TransferTimeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class FilterConfiguration {

    @Bean(name = "TRANSFER")
    public TransferTimeFilter transferTimeFilter() {
        return new TransferTimeFilter(Duration.ofHours(3), DurationFilterEnum.LESS);
    }
    @Bean("JETLAG")
    public JetlagFilter JetlagFilter() {
        return new JetlagFilter(JetlagFilterEnum.NO);
    }
    @Bean("DEPARTURE")
    public FlightDepartureDateFilter FlightDepartureDateFilter() {
        return new FlightDepartureDateFilter(LocalDateTime.now(), DateFilterEnum.AFTER);
    }

}