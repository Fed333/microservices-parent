package com.epam.javacc.microservices.servo.metrics.common.metric.observer;

import com.epam.javacc.microservices.servo.metrics.common.metric.MetricLogger;
import com.netflix.servo.Metric;
import com.netflix.servo.publish.BaseMetricObserver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogMetricObserver extends BaseMetricObserver {

    private final MetricLogger metricLogger;

    public LogMetricObserver(MetricLogger metricLogger) {
        super("log metric observer");
        this.metricLogger = metricLogger;
    }

    @Override
    public void updateImpl(List<Metric> list) {
        metricLogger.logMetrics(list);
    }
}
