package com.epam.javacc.microservices.servo.metrics;

import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.Metric;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.PeakRateCounter;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.publish.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

@Slf4j
public class CounterImplemenationsTest {

    @Test
    @SneakyThrows
    public void test_stepCounter() {
        System.setProperty("servo.pollers", "2000");
        Counter counter = new StepCounter(MonitorConfig.builder("test").build());

        assertEquals("counter should start with rate 0.0", 0.0, counter.getValue());

        counter.increment();
        counter.increment();
        counter.increment();
        counter.increment();
        SECONDS.sleep(2);

        assertEquals(
                "counter rate should have increased to 1.0",
                2.0, counter.getValue());
    }

    @Test
    @SneakyThrows
    public void test_stepPeakRate() {
        System.setProperty("servo.pollers", "60000");
        String value = "PeakRateCount";
        String key = "count";
        Counter counter = new PeakRateCounter(MonitorConfig.builder("test").withTag(key, value).build());

        MetricObserver metricObserver = new BaseMetricObserver("test_stepPeakRate") {
            @Override
            public void updateImpl(List<Metric> list) {
                List<Metric> collect = list.stream()
                        .filter(m -> m.getConfig().getTags().containsKey(key) && m.getConfig().getTags().getValue(key).equals(value))
                        .collect(Collectors.toList());
//                assertEquals(1, collect.size());
                if (collect.size() > 0) {
                    Metric metric = collect.get(0);
                    log.info("count: {}", metric.getNumberValue());
                }
            }
        };

        PollRunnable pollRunnable = new PollRunnable(new MonitorRegistryMetricPoller(), new BasicMetricFilter(true), metricObserver);

        PollScheduler.getInstance().start();

        PollScheduler.getInstance().addPoller(pollRunnable, 1, SECONDS);

        DefaultMonitorRegistry.getInstance().register(counter);

        assertEquals(
                "counter should start with 0",
                0, counter.getValue().intValue());

        counter.increment();
        counter.increment();
        counter.increment();
        SECONDS.sleep(2);

        counter.increment();
        counter.increment();
        counter.increment();
        counter.increment();

        SECONDS.sleep(2);
        SECONDS.sleep(2);

        counter.increment();
        counter.increment();

        assertEquals("peak rate should have be 4", 4, counter.getValue().intValue());
        SECONDS.sleep(60);
    }

    @After
    public void tearDown() {
        if (PollScheduler.getInstance().isStarted()) {
            PollScheduler.getInstance().stop();
        }
    }

}
