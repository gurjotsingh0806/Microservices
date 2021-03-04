package com.gurjot.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app1")
@EnableDiscoveryClient
@Slf4j
public class Hello {

    @GetMapping("/hello/{name}")
    @HystrixCommand(fallbackMethod = "getFallbackHelloEndpoint",
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
            })
    public String helloEndpoint(@PathVariable String name) {
        log.info("App1  method started");
//        for(int i = 0; i<10000; i++) {

//        }
        return "hello " + name + " from app 1";
    }

    public String getFallbackHelloEndpoint(@PathVariable String name) {
        return "From Fallback method in app 1";
    }
}
