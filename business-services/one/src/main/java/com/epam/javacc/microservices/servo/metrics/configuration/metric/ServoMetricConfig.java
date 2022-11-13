package com.epam.javacc.microservices.servo.metrics.configuration.metric;

import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MemoryMetricObserver;
import com.netflix.servo.publish.MetricFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class ServoMetricConfig {

    @PostConstruct
    private void init() {
        log.info("ServoMetricConfig#init invoked");
    }

    @Bean
    public MetricFilter basicMetricFilter() {
        return new BasicMetricFilter(true);
    }

    @Bean
    public MemoryMetricObserver basicMemoryObserver() {
        log.info("ServoMetricConfig#basicMemoryObserver invoked");
        return new MemoryMetricObserver();
    }

}
