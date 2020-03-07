package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author monda
 */
@RestController
@SpringBootApplication
public class SecurityOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOauth2Application.class);
    }

    /**
     * USER权限的用户可以访问
     *
     * @return
     */
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    /**
     * ADMIN权限的用户可以访问
     *
     * @return
     */
    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}
