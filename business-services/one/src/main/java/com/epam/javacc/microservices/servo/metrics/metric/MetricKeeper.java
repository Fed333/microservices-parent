package com.epam.javacc.microservices.servo.metrics.metric;

import com.netflix.servo.Metric;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.publish.MemoryMetricObserver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MetricKeeper {

    @Getter
    private final Map<String, Counter> counterMetrics = new HashMap<>();

    @Autowired
    private MemoryMetricObserver observer;

    @PostConstruct
    private void init() {
        log.info("MetricKeeper#init invoked!");
    }

    public Counter getCounter(String name) {
        Metric metric = observer.getObservations().stream().flatMap(Collection::stream)
                .filter(m -> m.getConfig().getName().equals(name))
                .findFirst().orElse(null);
        log.info("Metric {}", metric);
        return counterMetrics.get(name);
    }

}
