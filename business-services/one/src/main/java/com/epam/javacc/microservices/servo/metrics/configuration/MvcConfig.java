package com.epam.javacc.microservices.servo.metrics.configuration;

import com.epam.javacc.microservices.servo.metrics.configuration.interceptor.ControllerMetricsInterceptor;
import com.epam.javacc.microservices.servo.metrics.metric.MetricKeeper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final MetricKeeper metricKeeper;

    @Bean
    public ControllerMetricsInterceptor controllerMetricsInterceptor() {
        return new ControllerMetricsInterceptor(metricKeeper);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerMetricsInterceptor());
    }
}
