package com.epam.javacc.microservices.servo.metrics.metric;

import com.netflix.servo.Metric;
import com.netflix.servo.tag.TagList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.epam.javacc.microservices.servo.metrics.configuration.metric.PollSchedulerConfig.SERVO_POLLERS;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Component
public class MetricExtractor {

    @SuppressWarnings("unchecked")
    public Map<String, Map<String,Object>> extractMetricsMapPerEndpoint(List<Metric> metrics) {
        return metrics.stream()
                .collect(groupingBy(m -> m.getConfig().getName()))
                .entrySet().stream().map(e -> new Object[]{
                        e.getKey(),
                        e.getValue().stream()
                                .map(this::mapMetric)
                                .filter(Objects::nonNull)
                                .collect(toMap(o1 -> (String) o1[0], (Function<Object[], Object>) o2 -> o2[1]))
                }).collect(toMap(o -> (String) o[0], o -> (Map<String, Object>) o[1]));
    }

    private Object[] mapMetric(Metric m) {
        TagList tags = m.getConfig().getTags();
        if (tags.containsKey("type") && tags.getValue("type").equals("COUNTER")) {
            return new Object[]{
                    "totalCount",
                    m.getValue()
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("totalTime")) {
            //to obtain total method execution time within the polling interval
            //we need to multiply totalTime by servo.pollers interval (in seconds)
            return new Object[] {
                    "totalExecutionTime",
                    m.getNumberValue().doubleValue() * SERVO_POLLERS + " ms."
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("max")) {
            return new Object[] {
                    "maxExecutionTime",
                    m.getValue() + " ms."
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("count")) {
            return new Object[] {
                    "count",
                    Math.round(m.getNumberValue().doubleValue() * SERVO_POLLERS)
            };
        }
        return null;
    }

}
