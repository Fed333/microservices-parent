package com.epam.javacc.microservices.servo.metrics.common.monitor;

public interface MonitorsKeeper {

    <T> void putMonitor(String name, Class<T> monitorClass, T monitor);
    <T> T getMonitor(String name, Class<T> monitorClass);


}
