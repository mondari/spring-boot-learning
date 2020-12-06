package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AopApplication {

    public static final String HELLO_WORLD = "hello, world";

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

    /**
     * curl -X GET http://localhost:8080/hello
     *
     * @return "hello, world"
     */
    @GetMapping("hello")
    public String hello() {
        return HELLO_WORLD;
    }

}
