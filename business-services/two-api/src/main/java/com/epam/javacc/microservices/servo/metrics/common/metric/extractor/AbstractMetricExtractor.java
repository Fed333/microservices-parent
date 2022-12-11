package com.epam.javacc.microservices.servo.metrics.common.metric.extractor;

import com.netflix.servo.Metric;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * Class for extracting metrics to a more convenient format.
 * @author Roman_Kovalchuk
 * */
public abstract class AbstractMetricExtractor implements MetricExtractor {

    @SuppressWarnings("unchecked")
    public Map<String, Map<String,Object>> extractMetricsMapPerEndpoint(List<Metric> metrics) {
        return metrics.stream()
                .filter(this::isMetricIncluded)
                .collect(groupingBy(m -> m.getConfig().getName()))
                .entrySet().stream()
                .map(e -> new Object[]{
                        e.getKey(),
                        e.getValue().stream()
                                .map(this::mapMetric)
                                .filter(Objects::nonNull)
                                .collect(toMap(o1 -> (String) o1[0], (Function<Object[], Object>) o2 -> o2[1]))
                }).collect(toMap(o -> (String) o[0], o -> (Map<String, Object>) o[1]));
    }

    protected abstract Object[] mapMetric(Metric m);

    /**
     * Tells which kind of metric must be included.
     * */
    protected boolean isMetricIncluded(Metric metric) {
        return true;
    }

}
