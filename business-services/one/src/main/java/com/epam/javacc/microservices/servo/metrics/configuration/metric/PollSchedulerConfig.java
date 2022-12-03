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

    /**
     * Polling interval in seconds.
     * */
    public static final int SERVO_POLLERS = 30;

    private final MetricFilter basicMetricFilter;

    private final MetricObserver basicMetricObserver;

    @PostConstruct
    private void init() {
        log.info("PollSchedulerConfig#init invoked");
//        System.setProperty("servo.pollers", "600000");
        System.setProperty("servo.pollers", String.valueOf(SERVO_POLLERS * 1000));
        PollScheduler.getInstance().start();

        PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), basicMetricFilter, basicMetricObserver);
        PollScheduler.getInstance().addPoller(task, SERVO_POLLERS, TimeUnit.SECONDS);
    }

    @PreDestroy
    private void destroy() {
        log.info("PollSchedulerConfig#destroy invoked");
        if (PollScheduler.getInstance().isStarted()) {
            PollScheduler.getInstance().stop();
        }
    }

}
