package com.epam.javacc.microservices.servo.metrics.common.metric.extractor;

import com.netflix.servo.Metric;

import java.util.List;
import java.util.Map;

/**
 * Class for extracting metrics to a more convenient format.
 * @author Roman_Kovalchuk
 * */
public interface MetricExtractor {

    Map<String, Map<String,Object>> extractMetricsMapPerEndpoint(List<Metric> metrics);

}
