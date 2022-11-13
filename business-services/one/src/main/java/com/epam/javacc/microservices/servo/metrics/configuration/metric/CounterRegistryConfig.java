package com.epam.javacc.microservices.servo.metrics.configuration.metric;

import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.Monitors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Configuration
@DependsOn("counterMetricsConfig")
public class CounterRegistryConfig {

    @Resource
    private Counter postServiceOneCounter;

    @Resource
    private Counter getServiceOneInfoCounter;

    @Resource
    private Counter getServiceOneCounter;

    @PostConstruct
    private void init() {
        log.info("CounterRegistryConfig#init invoked!");

        DefaultMonitorRegistry.getInstance().register(getServiceOneCounter);
        DefaultMonitorRegistry.getInstance().register(postServiceOneCounter);
        DefaultMonitorRegistry.getInstance().register(getServiceOneInfoCounter);
    }

}
