package com.epam.javacc.microservices.servo.metrics.configuration.metric;

import com.netflix.servo.publish.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final MetricFilter basicMetricFilter;

    private final MemoryMetricObserver basicMemoryObserver;

    @PostConstruct
    private void init() {
        log.info("PollSchedulerConfig#init invoked");
        System.setProperty("servo.pollers", "600000");
        PollScheduler.getInstance().start();

        PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), basicMetricFilter, basicMemoryObserver);
        PollScheduler.getInstance().addPoller(task, 10, TimeUnit.MINUTES);
    }

    @PreDestroy
    private void destroy() {
        log.info("PollSchedulerConfig#destroy invoked");
        if (PollScheduler.getInstance().isStarted()) {
            PollScheduler.getInstance().stop();
        }
    }

}
