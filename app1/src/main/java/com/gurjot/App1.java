package com.gurjot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker //Or @EnableHysterix, Similar to @EnableDiscoveryClient vs @EnableEurekaClient
public class App1 {
    public static void main(String[] args) {
        SpringApplication.run(App1.class, args);
    }
}