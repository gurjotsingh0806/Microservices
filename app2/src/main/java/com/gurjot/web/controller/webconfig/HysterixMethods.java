package com.gurjot.web.controller.webconfig;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class HysterixMethods {

    @Autowired
    public RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackString",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
            })
    public String extracted(String url) throws InterruptedException {
//        wait(10);
        return restTemplate.getForEntity(url, String.class).getBody()+"from first method";
    }

    @HystrixCommand(fallbackMethod = "getFallbackString2",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
            })
    public String extractedMethod2(String url) throws InterruptedException {
//        wait(10);
        return restTemplate.getForEntity(url, String.class).getBody()+"from second method";
    }
    public String getFallbackString(@PathVariable String name) {
        log.info("Inside fallback1");

        return "fallback1";
    }
    public String getFallbackString2(@PathVariable String name) {
        log.info("Inside fallback2");
        return "fallback2";
    }
}
/*
apache avro - schema format in place of xml or json which we use to define data in our topic
Java object - if weused java object - serialized/deserialized it, it can only be read by java. But avro is lang independent so any1 can deserialize the data sent using avro serialization.
Also, avro is binary format so Can be shrunk by alot
So we use properties.put(key and value serializer class config, io.confluent........KafkaAvroSerializer.class)
To generate java classes from avro - we get maven plugin - avro-maven-plugin
Schema evolution- If we add new attributes to our avro schema, consumer will have to  update schema only if they wanna use the new element otherwise they can continue without breaking

* */