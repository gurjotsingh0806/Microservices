package com.gurjot.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app1")
public class Hello {

    @GetMapping("/hello/{name}")
    public String helloEndpoint(@PathVariable String name){
        return "hello " +name+ " from app 1";
    }
}
