package com.mondari.springbootaop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AOPController {

    /**
     * curl -X GET http://localhost:8080/demo
     *
     * @return "hello, world"
     */
    @GetMapping("demo")
    public String demo() {
        return "hello, world";
    }
}
