package com.epam.javacc.microservices.servo.metrics.observer.base;

import com.netflix.servo.publish.*;
import org.junit.After;
import org.junit.Before;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class MetricTestBase {

    protected MemoryMetricObserver observer;

    @Before
    public void prepareScheduler() {
        System.setProperty("servo.pollers", "1000");
        observer = new MemoryMetricObserver();
        PollScheduler
                .getInstance()
                .start();
        MetricFilter metricFilter = new BasicMetricFilter(true);
        PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), metricFilter, observer);
        PollScheduler
                .getInstance()
                .addPoller(task, 1, SECONDS);
    }

    @After
    public void stopScheduler() {
        if (PollScheduler
                .getInstance()
                .isStarted()) {
            PollScheduler
                    .getInstance()
                    .stop();
        }
    }
}