package com.epam.javacc.microservices.servo.metrics.common.configuration;

import com.epam.javacc.microservices.servo.metrics.common.metric.MetricLogger;
import com.epam.javacc.microservices.servo.metrics.common.metric.observer.LogMetricObserver;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MetricFilter;
import com.netflix.servo.publish.MetricObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ServoMetricConfig {

    private final MetricLogger metricLogger;

    @PostConstruct
    private void init() {
        log.info("ServoMetricConfig#init invoked");
    }

    @Bean
    public MetricFilter basicMetricFilter() {
        return new BasicMetricFilter(true) ;
    }

    @Bean
    public MetricObserver basicMetricObserver() {
        log.info("ServoMetricConfig#basicMetricObserver invoked");
        return new LogMetricObserver(metricLogger);
    }

}
