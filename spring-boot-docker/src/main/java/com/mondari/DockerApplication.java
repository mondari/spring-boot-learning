package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class DockerApplication {

    @RequestMapping("/")
    public String hello(ServletRequest httpRequest) {
        String localhost;
        try {
            localhost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localhost = "localhost";
        }
        return "Hello Docker World\n\n——from " + localhost + ":" + httpRequest.getLocalPort();
    }

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }

}
