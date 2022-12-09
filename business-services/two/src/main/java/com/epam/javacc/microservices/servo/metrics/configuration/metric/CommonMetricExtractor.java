package com.epam.javacc.microservices.servo.metrics.configuration.metric;

import com.epam.javacc.microservices.servo.metrics.common.metric.extractor.AbstractMetricExtractor;
import com.netflix.servo.Metric;
import com.netflix.servo.tag.TagList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.epam.javacc.microservices.servo.metrics.configuration.monitor.CounterMonitorRegister.COUNTER_TAG;
import static com.epam.javacc.microservices.servo.metrics.configuration.monitor.CounterMonitorRegister.COUNTER_TAG_VALUE;

@Component
public class CommonMetricExtractor extends AbstractMetricExtractor {

    /**
     * Special constant key, to identify which metrics should be extracted with
     * */
    public static String REGISTRY_TAG_KEY = "registry";

    /**
     * Special constant value, to identify which metrics should be extracted
     * */
    public static String REGISTRY_TAG_VALUE = CommonMetricExtractor.class.getName();

    /**
     * Polling metric interval in milliseconds.
     * */
    private final Long servoPollers;

    CommonMetricExtractor(@Value("${servo.pollers}") Long servoPollers) {
        this.servoPollers = servoPollers;
    }


    protected Object[] mapMetric(Metric m) {
        TagList tags = m.getConfig().getTags();
        if (isCountMonitor(tags)) {
            return new Object[]{
                    "stepCount",
                    m.getNumberValue().doubleValue()*TimeUnit.MILLISECONDS.toSeconds(servoPollers)
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("totalTime")) {
            return new Object[] {
                    "totalTime",
                    m.getNumberValue().doubleValue() + " ms."
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("avg")) {
            return new Object[] {
                    "averageTime",
                    m.getNumberValue().doubleValue() + " ms."
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("max")) {
            return new Object[] {
                    "maxExecutionTime",
                    m.getValue() + " ms."
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("min")) {
            return new Object[] {
                    "minExecutionTime",
                    m.getValue() + " ms."
            };
        }
        return null;
    }

    private static boolean isCountMonitor(TagList tags) {
        return tags.containsKey(COUNTER_TAG) && tags.getValue(COUNTER_TAG).equals(COUNTER_TAG_VALUE);
    }

    @Override
    protected boolean isMetricIncluded(Metric metric) {
        return  metric.getConfig().getTags().containsKey(REGISTRY_TAG_KEY) &&
                metric.getConfig().getTags().getValue(REGISTRY_TAG_KEY).equals(REGISTRY_TAG_VALUE);
    }

}
