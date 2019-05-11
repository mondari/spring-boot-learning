package com.mondari;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class AOPApplication {

    public static void main(String[] args) {
        SpringApplication.run(AOPApplication.class, args);
    }

    /**
     * curl -X GET http://localhost:8080/hello
     *
     * @return "hello, world"
     */
    @GetMapping("hello")
    public String hello() {
        return "hello, world";
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    CommandLineRunner runner(RestTemplate restTemplate) {
        return args -> System.out.println(restTemplate.getForObject("http://localhost:8080/hello/", String.class));
    }
}
