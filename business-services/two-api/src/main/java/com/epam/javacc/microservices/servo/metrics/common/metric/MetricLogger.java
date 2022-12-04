package com.epam.javacc.microservices.servo.metrics.common.metric;

import com.netflix.servo.Metric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetricLogger {

    private final MetricExtractor metricExtractor;

    public void logMetrics(List<Metric> observation) {
        log.info("[START LOG METRICS]");
        Map<String, Map<String,Object>> metrics = metricExtractor.extractMetricsMapPerEndpoint(observation);

        metrics.forEach((k,v)->{
            log.info("[METRIC] [\"endpoint\":\"{}\", \"totalCount\": \"{}\", \"count\": \"{}\", \"totalExecutionTime\": \"{}\", \"maxExecutionTime\": \"{}\" ]", k, v.get("totalCount"), v.get("count"), v.get("totalExecutionTime"), v.get("maxExecutionTime") );
        });
        log.info("[END LOG METRICS]");
    }

}
