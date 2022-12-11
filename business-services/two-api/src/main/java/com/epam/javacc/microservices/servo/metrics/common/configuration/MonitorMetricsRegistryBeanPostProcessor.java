package com.epam.javacc.microservices.servo.metrics.common.configuration;

import com.epam.javacc.microservices.servo.metrics.common.monitor.MonitorRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MonitorMetricsRegistryBeanPostProcessor implements BeanPostProcessor {

    @Autowired(required = false)
    private Optional<List<MonitorRegister>> registers;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RestController.class)) {
            if (bean.getClass().isAnnotationPresent(RequestMapping.class)) {
                String[] paths = bean.getClass().getAnnotation(RequestMapping.class).value();
                for (String path : paths) {
                    registerMonitors(bean, path);
                }
            } else {
                registerMonitors(bean, "");
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    private void registerMonitors(Object bean, String path) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class)){
                registerMonitor("GET", path, getMappedUrls(method.getAnnotation(GetMapping.class).value(), method.getAnnotation(GetMapping.class).path()));
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                registerMonitor("POST", path, getMappedUrls(method.getAnnotation(PostMapping.class).value(), method.getAnnotation(PostMapping.class).path()));
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                registerMonitor("PUT", path, getMappedUrls(method.getAnnotation(PutMapping.class).value(), method.getAnnotation(PutMapping.class).path()));
            } else if (method.isAnnotationPresent(PatchMapping.class)) {
                registerMonitor("PATCH", path, getMappedUrls(method.getAnnotation(PatchMapping.class).value(), method.getAnnotation(PatchMapping.class).path()));
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                registerMonitor("DELETE", path, getMappedUrls(method.getAnnotation(DeleteMapping.class).value(), method.getAnnotation(DeleteMapping.class).path()));
            } else if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping req = method.getAnnotation(RequestMapping.class);
                for (RequestMethod rm : req.method()) {
                    registerMonitor(rm.name(), path,  getMappedUrls(req.value(), req.path()));
                }
            }
        }
    }

    private void registerMonitor(String methodName, String path, String[] mappedUrls) {
        if (mappedUrls.length == 0) {
            registerMonitor(methodName + path);
        } else {
            for (String mappedUrl : mappedUrls) {
                mappedUrl = path + mappedUrl;
                String monitorName = methodName + mappedUrl;
                registerMonitor(monitorName);
            }
        }
    }

    private void registerMonitor(String monitorName) {
        registers.ifPresent(l -> l.forEach(r -> r.registerMonitor(monitorName)));
    }

    /**
     * Gives the array with mapped urls, which is used in mapping declaration.
     * @param value array declared using value attribute of mapped annotation
     * @param path array declared using path attribute of mapped annotation
     * @return not empty array if such exists
     * */
    private String[] getMappedUrls(String[] value, String[] path) {
        return value.length != 0 ? value : path;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}