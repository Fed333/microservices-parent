package com.epam.javacc.microservices.archaius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ArchaiusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchaiusApplication.class, args);
    }

}
