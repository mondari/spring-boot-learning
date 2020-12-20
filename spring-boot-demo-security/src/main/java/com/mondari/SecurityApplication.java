package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author limondar
 */
@RestController
@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class);
    }

    /**
     * 随意访问
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "hello";
    }

    /**
     * 认证通过的用户可以访问
     *
     * @return
     */
    @GetMapping("/user")
    public String user(Authentication authentication) {
        return authentication.getName();
    }

    /**
     * ADMIN权限的用户可以访问
     *
     * @return
     */
    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        return authentication.getName();
    }
}
