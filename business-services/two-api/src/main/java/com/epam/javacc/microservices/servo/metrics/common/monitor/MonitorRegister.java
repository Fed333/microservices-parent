package com.epam.javacc.microservices.servo.metrics.common.monitor;

import com.epam.javacc.microservices.servo.metrics.common.configuration.MonitorMetricsRegistryBeanPostProcessor;
import com.epam.javacc.microservices.servo.metrics.common.metric.MetricExtractor;

public interface MonitorRegister {

    /**
     * Special constant key, to identify which metrics should be extracted with {@link MetricExtractor}
     * */
    String REGISTRY_TAG_KEY = "registry";

    /**
     * Special constant value, to identify which metrics should be extracted with {@link MetricExtractor}
     * */
    String REGISTRY_TAG_VALUE = MonitorMetricsRegistryBeanPostProcessor.class.getName();

    void registerMonitor(String monitorName);

}
