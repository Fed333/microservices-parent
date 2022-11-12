package com.epam.javacc.microservices.servo.metrics.metric;

import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MetricKeeper {

    private final Map<String, Counter> counterMetrics = new HashMap<>();

    @PostConstruct
    private void init() {
        log.info("MetricKeeper#init invoked!");
        counterMetrics.put("GET/service-one", new BasicCounter(MonitorConfig.builder("GET/service-one").build()));
        counterMetrics.put("POST/service-one", new BasicCounter(MonitorConfig.builder("POST/service-one").build()));
        counterMetrics.put("GET/service-one/info", new BasicCounter(MonitorConfig.builder("GET/service-one/info").build()));
    }

    public Counter getCounter(String name) {
        return counterMetrics.get(name);
    }

}
