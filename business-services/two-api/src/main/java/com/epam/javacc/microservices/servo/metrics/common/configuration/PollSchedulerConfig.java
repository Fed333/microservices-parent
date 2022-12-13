package com.epam.javacc.microservices.servo.metrics.common.configuration;

import com.netflix.servo.publish.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@DependsOn({"servoMetricConfig"})
@RequiredArgsConstructor
public class PollSchedulerConfig {

    /**
     * Polling interval in seconds.
     * */
    @Value("${servo.pollers}")
    private Long servoPollers;

    private final MetricFilter basicMetricFilter;

    private final MetricObserver basicMetricObserver;

    @PostConstruct
    private void init() {
        log.info("PollSchedulerConfig#init invoked");
        System.setProperty("servo.pollers", String.valueOf(servoPollers));
        log.info("Set servo pollers to: {} ms.", servoPollers);
        PollScheduler.getInstance().start();

        PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), basicMetricFilter, basicMetricObserver);
        PollScheduler.getInstance().addPoller(task, servoPollers, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    private void destroy() {
        log.info("PollSchedulerConfig#destroy invoked");
        if (PollScheduler.getInstance().isStarted()) {
            PollScheduler.getInstance().stop();
        }
    }

}
