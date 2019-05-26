package com.mondari;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(JdbcProperties jdbcProperties) {
        return args -> {
            System.out.println("username" + jdbcProperties.getUsername());
            System.out.println("password" + jdbcProperties.getPassword());
        };
    }
}
