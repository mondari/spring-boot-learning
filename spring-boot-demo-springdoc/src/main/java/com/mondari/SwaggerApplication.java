package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@SpringBootApplication
public class SwaggerApplication {

    @GetMapping("/world")
    public String world(ServletRequest httpRequest) {
        return sayHello(httpRequest);
    }

    @GetMapping("/hello")
    public String hello(ServletRequest httpRequest) {
        return sayHello(httpRequest);
    }

    private String sayHello(ServletRequest httpRequest) {
        String localhost;
        try {
            localhost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localhost = "localhost";
        }
        return "Hello springdoc\n\n——from " + localhost + ":" + httpRequest.getLocalPort();
    }

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }

}

