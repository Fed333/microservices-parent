package com.epam.javacc.microservices.servo.metrics.configuration.interceptor;

import com.epam.javacc.microservices.servo.metrics.configuration.monitor.MonitorsFacade;
import com.netflix.servo.monitor.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ControllerMetricsInterceptor implements HandlerInterceptor {

    private final MonitorsFacade monitorsFacade;

    private final ThreadLocal<Stopwatch> stopwatchThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[BEGIN] {} {}", request.getMethod(), request.getRequestURI());
        String metricName = buildMetricName(request);
        Optional.ofNullable(monitorsFacade.getCounter(metricName)).ifPresent(c->{
            log.info("Increment {} by one", metricName);
            c.increment();
        });

        Optional.ofNullable(monitorsFacade.getTimer(metricName)).ifPresent(t->{
            log.info("Timer start of execution {}...", metricName);
            stopwatchThreadLocal.set(t.start());
        });
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Stopwatch stopwatch = stopwatchThreadLocal.get();
        if (Objects.nonNull(stopwatch)) {
            stopwatch.stop();
            log.info("Timer end of execution {}.", buildMetricName(request));
            log.info("Duration: {} ms.", stopwatch.getDuration(TimeUnit.MILLISECONDS));
            log.info("Total time: {} ms.", monitorsFacade.getTimer(buildMetricName(request)).getTotalTime());
            stopwatchThreadLocal.set(null);
        }
        log.info("[END] {} {}", request.getMethod(), request.getRequestURI());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    private String buildMetricName(HttpServletRequest request) {
        return request.getMethod() + request.getRequestURI();
    }

}
