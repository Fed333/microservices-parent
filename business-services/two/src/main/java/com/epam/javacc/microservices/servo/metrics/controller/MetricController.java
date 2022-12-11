package com.epam.javacc.microservices.servo.metrics.controller;


import com.epam.javacc.microservices.servo.metrics.common.utils.MapUtils;
import com.epam.javacc.microservices.servo.metrics.configuration.monitor.MonitorsFacade;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.StatsTimer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/service-two/metrics")
public class MetricController {

    @Value("${servo.pollers}")
    private Long servoPollers;

    private final MonitorsFacade monitorsFacade;

    @GetMapping("/counter")
    public Map<String, Object> getCounterMetric(@RequestParam(name = "name") String name) {
        log.info("stepCounter: {}", monitorsFacade.getCounter(name).getValue().doubleValue());
        double count = monitorsFacade.getCounter(name).getValue().doubleValue() * TimeUnit.MILLISECONDS.toSeconds(servoPollers);

        return MapUtils.createMap(new Object[][]{
                {"method", name},
                {"count", count}
        });
    }

    @GetMapping("/timer")
    public Map<String, Object> getTimerMetric(@RequestParam(name = "name") String name) {
        StatsTimer timer = monitorsFacade.getTimer(name);
        if (Objects.nonNull(timer)) {
            if (timer.getCount() == 0){
                return MapUtils.createMap(new Object[][]{
                        {"method", name},
                        {"message", "This method hasn't been invoked yet in this metric interval!"}
                });
            } else {
                double avgTime = (double)timer.getTotalTime() / timer.getCount();
                return MapUtils.createMap(new Object[][]{
                        {"method", name},
                        {"averageTime", String.format("%.2f", avgTime) + " ms."}
                });
            }
        }
        return MapUtils.createMap(new Object[][]{
                {"method", name},
                {"message", "timer wasn't found."}
        });
    }

}
