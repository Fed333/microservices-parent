package com.epam.javacc.microservices.apigateway.zuul;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
public class ZuulProxyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyServerApplication.class, args);
    }

}
