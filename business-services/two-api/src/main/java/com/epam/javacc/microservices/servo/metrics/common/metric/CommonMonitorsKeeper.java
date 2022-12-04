package com.epam.javacc.microservices.servo.metrics.common.metric;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorsKeeper;
import com.netflix.servo.monitor.Monitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommonMonitorsKeeper implements MonitorsKeeper {

    private final Map<String, Monitor<Object>> monitors = new HashMap<>();

    @PostConstruct
    private void init() {
        log.info("MetricKeeper#init invoked!");
    }

    @Override
    public <T> void putMonitor(String name, Class<T> monitorClass, T monitor) {
        monitors.put(buildCompositeKey(name, monitorClass), (Monitor<Object>) monitor);
    }

    @Override
    public <T> T getMonitor(String name, Class<T> monitorClass) {
        Monitor<Object> objectMonitor = monitors.get(buildCompositeKey(name, monitorClass));
        return (T) objectMonitor;
    }

    private static <T> String buildCompositeKey(String name, Class<T> monitorClass) {
        return name + "#" + monitorClass.getName();
    }

}
