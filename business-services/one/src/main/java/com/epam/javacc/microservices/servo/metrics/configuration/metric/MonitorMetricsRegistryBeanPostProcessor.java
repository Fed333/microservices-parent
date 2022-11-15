package com.epam.javacc.microservices.servo.metrics.configuration.metric;

import com.epam.javacc.microservices.servo.metrics.metric.MetricKeeper;
import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MonitorMetricsRegistryBeanPostProcessor implements BeanPostProcessor {

    private final MetricKeeper metricKeeper;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RestController.class)) {
            if (bean.getClass().isAnnotationPresent(RequestMapping.class)) {
                String[] paths = bean.getClass().getAnnotation(RequestMapping.class).value();
                for (String path : paths) {
                    registerMonitors(bean, path);
                }
            } else {
                registerMonitors(bean, "");
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    private void registerMonitors(Object bean, String path) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class)){
                registerMonitor("GET", path, method.getAnnotation(GetMapping.class).value());
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                registerMonitor("POST", path, method.getAnnotation(PostMapping.class).value());
            }
        }
    }

    private void registerMonitor(String methodName, String path, String[] mappedUrls) {
        if (mappedUrls.length == 0) {
            registerMonitor(methodName + path);
        } else {
            for (String mappedUrl : mappedUrls) {
                mappedUrl = path + mappedUrl;
                String monitorName = methodName + mappedUrl;
                registerMonitor(monitorName);
            }
        }
    }

    private void registerMonitor(String monitorName) {
        registerCounterMonitor(monitorName);
        registerTimerMonitor(monitorName);
    }

    private void registerCounterMonitor(String monitorName) {
        log.info("Register counter monitor with name {}", monitorName);
        Counter counter = new BasicCounter(MonitorConfig.builder(monitorName).build());
        metricKeeper.getCounterMetrics().put(monitorName, counter);
        DefaultMonitorRegistry.getInstance().register(counter);
    }

    private void registerTimerMonitor(String monitorName) {
        log.info("Register timer monitor with name {}", monitorName);
        BasicTimer timer = new BasicTimer(MonitorConfig.builder(monitorName).build(), TimeUnit.MILLISECONDS);
        metricKeeper.getTimerMonitors().put(monitorName, timer);
        DefaultMonitorRegistry.getInstance().register(timer);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}