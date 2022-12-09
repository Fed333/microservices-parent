package com.epam.javacc.microservices.servo.metrics.configuration.monitor;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorsKeeper;
import com.netflix.servo.monitor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Facade, designed especially for obtaining all available monitors through the application.
 * @author Roman_Kovalchuk
 * */
@Component
@RequiredArgsConstructor
public class MonitorsFacade {

    private final MonitorsKeeper monitorsKeeper;

    public StepCounter getCounter(String name) {
        return monitorsKeeper.getMonitor(name, StepCounter.class);
    }

    public StatsTimer getTimer(String name) {
        return monitorsKeeper.getMonitor(name, StatsTimer.class);
    }

}
