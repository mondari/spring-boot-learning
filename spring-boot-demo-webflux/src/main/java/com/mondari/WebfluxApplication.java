package com.mondari;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class WebfluxApplication {

    public static final String WELCOME_TO_REACTIVE_WORLD = "Welcome to reactive world ~";

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebfluxApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }

    /**
     * 访问 http://localhost:8080/hello
     *
     * @return
     */
    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just(WELCOME_TO_REACTIVE_WORLD);
    }

}
