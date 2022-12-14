package com.epam.javacc.microservices.servo.metrics.configuration.metric;

import com.epam.javacc.microservices.servo.metrics.common.configuration.MonitorMetricsRegistryBeanPostProcessor;
import com.epam.javacc.microservices.servo.metrics.common.metric.extractor.AbstractMetricExtractor;
import com.netflix.servo.Metric;
import com.netflix.servo.tag.TagList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CommonMetricExtractor extends AbstractMetricExtractor {

    /**
     * Special constant key, to identify which metrics should be extracted with
     * */
    public static String REGISTRY_TAG_KEY = "registry";

    /**
     * Special constant value, to identify which metrics should be extracted
     * */
    public static String REGISTRY_TAG_VALUE = MonitorMetricsRegistryBeanPostProcessor.class.getName();

    private final Long servoPollers;

    CommonMetricExtractor(@Value("${servo.pollers}") Long servoPollers) {
        this.servoPollers = servoPollers;
    }


    protected Object[] mapMetric(Metric m) {
        TagList tags = m.getConfig().getTags();
        if (isTotalCountMonitor(tags)) {
            return new Object[]{
                    "totalCount",
                    m.getValue()
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("totalTime")) {
            //to obtain total method execution time within the polling interval
            //we need to multiply totalTime by servo.pollers interval (in seconds)
            return new Object[] {
                    "totalExecutionTime",
                    m.getNumberValue().doubleValue() * TimeUnit.MILLISECONDS.toSeconds(servoPollers) + " ms."
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("max")) {
            return new Object[] {
                    "maxExecutionTime",
                    m.getValue() + " ms."
            };
        } else if (tags.containsKey("statistic") && tags.getValue("statistic").equals("count")) {
            return new Object[] {
                    "count",
                    Math.round(m.getNumberValue().doubleValue() * TimeUnit.MILLISECONDS.toSeconds(servoPollers))
            };
        }
        return null;
    }

    private static boolean isTotalCountMonitor(TagList tags) {
        return tags.containsKey("type") && tags.getValue("type").equals("COUNTER");
    }

    @Override
    protected boolean isMetricIncluded(Metric metric) {
        return  metric.getConfig().getTags().containsKey(REGISTRY_TAG_KEY) &&
                metric.getConfig().getTags().getValue(REGISTRY_TAG_KEY).equals(REGISTRY_TAG_VALUE);
    }

}
