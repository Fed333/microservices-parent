package com.epam.javacc.microservices.servo.metrics.controller;


import com.epam.javacc.microservices.servo.metrics.metric.MetricKeeper;
import com.epam.javacc.microservices.servo.metrics.utils.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/metrics")
public class MetricController {

    private final MetricKeeper metricKeeper;

    @GetMapping("/counter")
    public Map<String, Object> getCounterMetric(@RequestParam(name = "name") String name) {
        long count = metricKeeper.getCounter(name).getValue().longValue();
        return MapUtils.createMap(new Object[][]{
                {"method", name},
                {"count", count}
        });
    }


}
