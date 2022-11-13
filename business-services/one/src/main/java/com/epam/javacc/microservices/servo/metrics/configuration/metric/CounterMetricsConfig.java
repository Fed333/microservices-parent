package com.epam.javacc.microservices.servo.metrics.configuration.metric;

import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CounterMetricsConfig {

    @Bean
    public Counter getServiceOneCounter(){
      return  new BasicCounter(MonitorConfig.builder("GET/service-one").build());
    }

    @Bean
    public Counter postServiceOneCounter(){
      return  new BasicCounter(MonitorConfig.builder("POST/service-one").build());
    }

    @Bean
    public Counter getServiceOneInfoCounter() {
        return new BasicCounter(MonitorConfig.builder("GET/service-one/info").build());
    }

}
