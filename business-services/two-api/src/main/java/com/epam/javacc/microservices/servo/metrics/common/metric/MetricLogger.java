package com.epam.javacc.microservices.servo.metrics.common.metric;

import com.epam.javacc.microservices.servo.metrics.common.metric.extractor.MetricExtractor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.servo.Metric;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetricLogger {

    private final MetricExtractor metricExtractor;

    @SneakyThrows
    public void logMetrics(List<Metric> observation)  {
        log.info("[START LOG METRICS]");
        Map<String, Map<String,Object>> metrics = metricExtractor.extractMetricsMapPerEndpoint(observation);

        for (Map.Entry<String, Map<String, Object>> entry : metrics.entrySet()) {
            String k = entry.getKey();
            Map<String, Object> v = entry.getValue();
            v.put("endpoint", k);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(v);
            log.info("[METRIC] [{}]", jsonString);
        }
        log.info("[END LOG METRICS]");
    }

}
