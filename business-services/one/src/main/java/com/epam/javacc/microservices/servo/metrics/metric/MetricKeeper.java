package com.epam.javacc.microservices.servo.metrics.metric;

import com.netflix.servo.Metric;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.publish.MemoryMetricObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
@Slf4j
public class MetricKeeper {

    private final Map<String, Counter> counterMetrics = new HashMap<>();

    @Autowired
    private MemoryMetricObserver observer;

    @Resource
    private Counter postServiceOneCounter;


    @Resource
    private Counter getServiceOneInfoCounter;

    @Resource
    private Counter getServiceOneCounter;

    @PostConstruct
    private void init() {
        log.info("MetricKeeper#init invoked!");
//        counterMetrics.put("GET/service-one", new BasicCounter(MonitorConfig.builder("GET/service-one").build()));
//        counterMetrics.put("POST/service-one", new BasicCounter(MonitorConfig.builder("POST/service-one").build()));
//        counterMetrics.put("GET/service-one/info", new BasicCounter(MonitorConfig.builder("GET/service-one/info").build()));
        counterMetrics.put("GET/service-one", getServiceOneCounter);
        counterMetrics.put("POST/service-one", postServiceOneCounter);
        counterMetrics.put("GET/service-one/info", getServiceOneInfoCounter);
    }

    public Counter getCounter(String name) {
        Metric metric = observer.getObservations().stream().flatMap(Collection::stream)
                .filter(m -> m.getConfig().getName().equals(name))
                .findFirst().orElse(null);
        log.info("Metric {}", metric);
        return counterMetrics.get(name);
    }

}
