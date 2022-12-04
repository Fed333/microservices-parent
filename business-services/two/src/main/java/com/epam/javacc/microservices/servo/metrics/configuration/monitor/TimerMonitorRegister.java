package com.epam.javacc.microservices.servo.metrics.configuration.monitor;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorRegister;
import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorsKeeper;
import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.MonitorConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.epam.javacc.microservices.servo.metrics.configuration.metric.CommonMetricExtractor.REGISTRY_TAG_KEY;
import static com.epam.javacc.microservices.servo.metrics.configuration.metric.CommonMetricExtractor.REGISTRY_TAG_VALUE;

/**
 * Class for registering {@link BasicTimer} monitor metrics to the application.
 * @author Roman_Kovalchuk
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class TimerMonitorRegister implements MonitorRegister {

    private final MonitorsKeeper monitorsKeeper;

    @Override
    public void registerMonitor(String monitorName) {
        log.info("Register timer monitor with name {}", monitorName);
        BasicTimer timer = new BasicTimer(MonitorConfig.builder(monitorName).withTag(REGISTRY_TAG_KEY, REGISTRY_TAG_VALUE).build(), TimeUnit.MILLISECONDS);
        monitorsKeeper.putMonitor(monitorName, BasicTimer.class, timer);
        DefaultMonitorRegistry.getInstance().register(timer);
        log.info("Register counter monitor with name {}", monitorName);
    }
}
