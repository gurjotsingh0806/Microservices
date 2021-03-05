package com.gurjot.web.controller;

import com.gurjot.web.resource.PersonResource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@EnableDiscoveryClient
@Slf4j
public class PersonController {

    private static List<PersonResource> personResourceList = new ArrayList<>();

    @Value("${username2: DefaultUsername}")
    private String username2;

    @GetMapping(
            value = "/vaultConfigMsg",
            produces = { "application/json" }
    )
    public ResponseEntity<String> getMessageFromCloudConfigVault() {
        return ResponseEntity.status(HttpStatus.OK).body("Username from Vault based cloud config = " +username2);
    }

    @PostMapping(
            value = "/persons",
            produces = { "application/json "},
            consumes = { "application/json "}
    )
    public ResponseEntity<String> addPerson(@RequestBody PersonResource personResource) {
        personResourceList.add(personResource);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResource.getFirstName()+ personResource.getLastName());
    }

    @GetMapping(
            value = "/persons",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<List<PersonResource>> getPerson(
            @RequestParam(name = "count", defaultValue = "2", required = false) int count) {
        List<PersonResource> countLimitList = personResourceList.stream().limit(count).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(countLimitList);
    }

    @GetMapping("/{name}")
    @HystrixCommand(fallbackMethod = "getFallbackHelloEndpoint",
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
            })
    public String helloEndpoint(@PathVariable String name) {
        log.info("App1  method started");

        return "hello " + name + " from app 1";
    }

    public String getFallbackHelloEndpoint(@PathVariable String name) {
        return "From Fallback method in app 1";
    }
}
