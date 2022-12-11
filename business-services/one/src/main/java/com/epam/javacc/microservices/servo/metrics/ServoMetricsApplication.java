package com.epam.javacc.microservices.servo.metrics;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServoMetricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServoMetricsApplication.class, args);
    }

}
