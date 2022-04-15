package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author limondar
 * @date 2020/3/6
 */
@RestController
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class);
    }

    @GetMapping(value = {"/hello", "/"})
    public String hello() {
        return "hello";
    }

    /**
     * 认证通过的即可访问
     *
     * @return
     */
    @GetMapping(value = {"/principal", "/res/me"})
    public Principal getUser(Principal principal) {
        return principal;
    }

    /**
     * USER权限的用户可以访问
     *
     * @return
     */
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal(expression = "username") String name) {
        return "hello " + name;
    }

    /**
     * ADMIN权限的用户可以访问
     *
     * @return
     */
    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal(expression = "username") String name) {
        return "hello " + name;
    }

}
