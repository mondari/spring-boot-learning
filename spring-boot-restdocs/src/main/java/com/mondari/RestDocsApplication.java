package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RestDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestDocsApplication.class, args);
    }

    @GetMapping("hello")
    String hello() {
        return "hello world";
    }
}
