package com.gurjot.web.controller;

import com.gurjot.web.controller.resource.PersonResource;
import com.gurjot.web.controller.webconfig.HysterixMethods;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/app2")
@RefreshScope // for cloud config
//@EnableAutoConfiguration
@Slf4j
public class ConsumerToApp1 {

    @Value("${message}")
    private String message;
    @Autowired
    public RestTemplate restTemplate;
    @Autowired
    HysterixMethods hysterixMethods;
    // ote - Lets say we make a regualr method that does a call on another method which is marked with Hysterix ( So methods within the same class), In this case hysterix will nto be able to intercept the call since it uses proxy mechanism. So to solve this we would have to move the other method marked with hysterix to an outer class

    String baseUrl="http://app1/api/v1/";

    @GetMapping("/{firstName}/{lastName}")
    public String createPersonCallToApp1(@PathVariable String firstName, @PathVariable String lastName){
        log.info("Creating person object");
        PersonResource personResource = new PersonResource(firstName,lastName);
        HttpEntity<PersonResource> request = new HttpEntity<>(personResource);
        String url = baseUrl+"persons";
        log.info("making call to :" +url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return "Person object created with name :" +response.getBody();
    }

    @GetMapping
    @HystrixCommand(fallbackMethod = "getFallBack",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")
        })
    public String noResponse() {
        for(int i=0; i<=Integer.MAX_VALUE; i++){
        }
        return "fallback didnt work";
    }

    public String getFallBack() {
        return "From Hystrix fallback method";
    }
    @GetMapping("/cloudConfigMsg")
//    @HystrixCommand(fallbackMethod = "getFallbackString")
    public String app2Endpoint() {//@PathVariable String name) throws InterruptedException {
        log.info("App2  method started");
//        String url = baseUrl+name;
//        String a = hysterixMethods.extracted(url);
//        String b = hysterixMethods.extractedMethod2(url);
//        String c = message;
//        log.info(a+b+c);
        return message;

    }
//
//    @HystrixCommand(fallbackMethod = "getFallbackString",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")
//            })
//    private String extracted(String url) throws InterruptedException {
//        wait(10);
//        return restTemplate.getForEntity(url, String.class).getBody()+"from first method";
//    }
//
//    @HystrixCommand(fallbackMethod = "getFallbackString2",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")
//            })
//    private String extractedMethod2(String url) throws InterruptedException {
//        wait(10);
//        return restTemplate.getForEntity(url, String.class).getBody()+"from second method";
//    }
//    public String getFallbackString(@PathVariable String name) {
//        log.info("Inside fallback1");
//
//        return "fallback1";
//    }
//    public String getFallbackString2(@PathVariable String name) {
//        log.info("Inside fallback2");
//        return "fallback2";
//    }
}
