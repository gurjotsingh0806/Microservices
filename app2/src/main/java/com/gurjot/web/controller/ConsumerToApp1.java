package com.gurjot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/app2")
@RefreshScope // for cloud config
//@EnableAutoConfiguration
public class ConsumerToApp1 {

    @Value("${message: defaultMsgFromApp2}")
    private String message;
    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("/{name}")
    public String app2Endpoint(@PathVariable String name) {
        String url = "http://localhost:8080/app1/hello/" +name;
        String app1 = restTemplate.getForEntity(url, String.class).getBody();
        return app1+message;

    }
}
