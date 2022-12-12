package com.epam.javacc.microservices.servo.metrics;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServoMetricsApplicationOne {

    public static void main(String[] args) {
        //TODO remove hardcode to reading application properties.
        // It's temporarily solution of problem with setting servo.pollers constant.
        // The actual value of metrics refreshing was default all the time.
        System.setProperty("servo.pollers", "30000");
        SpringApplication.run(ServoMetricsApplicationOne.class, args);
    }

}
