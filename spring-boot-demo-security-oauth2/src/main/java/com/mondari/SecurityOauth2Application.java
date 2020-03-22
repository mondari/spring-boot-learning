package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
     * 认证通过的都可以访问
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    /**
     * USER权限的用户可以访问
     *
     * @return
     */
    @GetMapping("/user/hello")
    public String user(@AuthenticationPrincipal(expression = "username") String name) {
        return "hello " + name;
    }

    /**
     * ADMIN权限的用户可以访问
     *
     * @return
     */
    @GetMapping("/admin/hello")
    public String admin(@AuthenticationPrincipal(expression = "username") String name) {
        return "hello " + name;
    }

    @GetMapping(value = "/principal")
    public Principal getUser(Principal principal) {
        return principal;
    }
}
