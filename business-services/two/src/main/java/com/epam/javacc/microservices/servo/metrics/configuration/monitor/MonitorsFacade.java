package com.epam.javacc.microservices.servo.metrics.configuration.monitor;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorsKeeper;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.BasicTimer;
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

    public BasicCounter getCounter(String name) {
        return monitorsKeeper.getMonitor(name, BasicCounter.class);
    }

    public BasicTimer getTimer(String name) {
        return monitorsKeeper.getMonitor(name, BasicTimer.class);
    }

}
