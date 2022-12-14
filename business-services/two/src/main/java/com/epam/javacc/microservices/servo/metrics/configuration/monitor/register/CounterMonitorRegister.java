package com.epam.javacc.microservices.servo.metrics.configuration.monitor.register;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorRegister;
import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorsKeeper;
import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.StepCounter;
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

    public static final String COUNTER_TAG = "counter";
    public static final String COUNTER_TAG_VALUE = StepCounter.class.getSimpleName();

    private final MonitorsKeeper monitorsKeeper;

    @Override
    public void registerMonitor(String monitorName) {
        log.info("Register counter monitor with name {}", monitorName);
        StepCounter counter = new StepCounter(MonitorConfig.builder(monitorName)
                .withTag(REGISTRY_TAG_KEY, REGISTRY_TAG_VALUE)
                .withTag(COUNTER_TAG, COUNTER_TAG_VALUE).build()
        );
        monitorsKeeper.putMonitor(monitorName, StepCounter.class, counter);
        DefaultMonitorRegistry.getInstance().register(counter);
    }
}
