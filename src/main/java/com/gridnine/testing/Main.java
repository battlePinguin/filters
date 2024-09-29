package com.gridnine.testing;

import com.gridnine.testing.service.FlightFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        FlightFilter flightFilter = context.getBean(FlightFilter.class);
        flightFilter.processFlights();
    }
}
