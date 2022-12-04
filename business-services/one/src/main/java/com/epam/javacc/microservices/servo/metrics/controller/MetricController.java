package com.epam.javacc.microservices.servo.metrics.controller;


import com.epam.javacc.microservices.servo.metrics.common.utils.MapUtils;
import com.epam.javacc.microservices.servo.metrics.configuration.monitor.MonitorsFacade;
import com.netflix.servo.monitor.BasicTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-one/metrics")
public class MetricController {

//    private final MonitorsKeeper metricKeeper;

    private final MonitorsFacade monitorsFacade;

    @GetMapping("/counter")
    public Map<String, Object> getCounterMetric(@RequestParam(name = "name") String name) {
        long count = monitorsFacade.getCounter(name).getValue().longValue();
        return MapUtils.createMap(new Object[][]{
                {"method", name},
                {"count", count}
        });
    }

    @GetMapping("/timer")
    public Map<String, Object> getTimerMetric(@RequestParam(name = "name") String name) {
        BasicTimer timer = monitorsFacade.getTimer(name);
        if (Objects.nonNull(timer)) {
            if (timer.getCount() == 0){
                return MapUtils.createMap(new Object[][]{
                        {"method", name},
                        {"message", "This method hasn't been invoked yet in this metric interval!"}
                });
            } else {
                double avgTime = timer.getTotalTime() / timer.getCount();
                double maxTime = timer.getMax();
                return MapUtils.createMap(new Object[][]{
                        {"method", name},
                        {"averageTime", String.format("%.2f", avgTime) + " ms."},
                        {"maximumTime", String.format("%.2f", maxTime) + " ms."},
                });
            }
        }
        return MapUtils.createMap(new Object[][]{
                {"method", name},
                {"message", "timer wasn't found."}
        });
    }

}
