package com.epam.javacc.microservices.servo.metrics.configuration.interceptor;

import com.epam.javacc.microservices.servo.metrics.metric.MetricKeeper;
import com.netflix.servo.monitor.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ControllerMetricsInterceptor implements HandlerInterceptor {

    private final MetricKeeper metricKeeper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[BEGIN] {} {}", request.getMethod(), request.getRequestURI());
        Optional.ofNullable(metricKeeper.getCounter(buildMetricName(request))).ifPresent(c->{
            log.info("Increment {} by one", buildMetricName(request));
            c.increment();
        });
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[END] {} {}", request.getMethod(), request.getRequestURI());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    private String buildMetricName(HttpServletRequest request) {
        return request.getMethod() + request.getRequestURI();
    }

}
