package com.epam.javacc.microservices.servo.metrics.configuration.monitor;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorRegister;
import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorsKeeper;
import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.MonitorConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.epam.javacc.microservices.servo.metrics.configuration.metric.CommonMetricExtractor.REGISTRY_TAG_KEY;
import static com.epam.javacc.microservices.servo.metrics.configuration.metric.CommonMetricExtractor.REGISTRY_TAG_VALUE;

/**
 * Class for registering {@link BasicCounter} monitor metrics to the application.
 * @author Roman_Kovalchuk
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class CounterMonitorRegister implements MonitorRegister {

    private final MonitorsKeeper monitorsKeeper;

    @Override
    public void registerMonitor(String monitorName) {
        log.info("Register counter monitor with name {}", monitorName);
        BasicCounter counter = new BasicCounter(MonitorConfig.builder(monitorName).withTag(REGISTRY_TAG_KEY, REGISTRY_TAG_VALUE).build());
        monitorsKeeper.putMonitor(monitorName, BasicCounter.class, counter);
        DefaultMonitorRegistry.getInstance().register(counter);
    }
}
