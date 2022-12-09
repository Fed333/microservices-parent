package com.epam.javacc.microservices.servo.metrics.configuration.monitor;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorRegister;
import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorsKeeper;
import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.StatsTimer;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.stats.StatsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.epam.javacc.microservices.servo.metrics.configuration.metric.CommonMetricExtractor.REGISTRY_TAG_KEY;
import static com.epam.javacc.microservices.servo.metrics.configuration.metric.CommonMetricExtractor.REGISTRY_TAG_VALUE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Class for registering {@link BasicTimer} monitor metrics to the application.
 * @author Roman_Kovalchuk
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class TimerMonitorRegister implements MonitorRegister {

    @Value("${servo.pollers}")
    private Long servoPollers;

    public static final String TIMER_TAG = "timer";
    public static final String TIMER_TAG_VALUE = StatsTimer.class.getSimpleName();

    private final MonitorsKeeper monitorsKeeper;

    //TODO change {@link Timer} implementation for two module.
    @Override
    public void registerMonitor(String monitorName) {
        log.info("Register timer monitor with name {}", monitorName);
        StatsTimer timer = new StatsTimer(MonitorConfig.builder(monitorName)
                .withTag(REGISTRY_TAG_KEY, REGISTRY_TAG_VALUE)
                .withTag(TIMER_TAG, TIMER_TAG_VALUE).build(), new StatsConfig.Builder()
                .withPublishMax(true)
                .withPublishMin(true)
                .withPublishCount(true)
                .withPublishMean(true)
                .withComputeFrequencyMillis(servoPollers).build(), MILLISECONDS);
        monitorsKeeper.putMonitor(monitorName, StatsTimer.class, timer);
        DefaultMonitorRegistry.getInstance().register(timer);
        log.info("Register counter monitor with name {}", monitorName);
    }
}
