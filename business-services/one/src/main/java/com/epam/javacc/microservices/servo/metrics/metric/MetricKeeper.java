package com.epam.javacc.microservices.servo.metrics.metric;

import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Counter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MetricKeeper {

    @Getter
    private final Map<String, Counter> counterMonitors = new HashMap<>();

    @Getter
    private final Map<String, BasicTimer> timerMonitors = new HashMap<>();

    @Autowired
    private MetricLogger metricLogger;

    @PostConstruct
    private void init() {
        log.info("MetricKeeper#init invoked!");
    }

    public Counter getCounter(String name) {
        return counterMonitors.get(name);
    }

    public BasicTimer getTimer(String name) {
        return timerMonitors.get(name);
    }

}
