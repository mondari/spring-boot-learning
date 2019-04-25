package com.mondari.controller;

import com.mondari.model.Person;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestfulController {

    @GetMapping("/hello/{name}")
    public String helloWithPath(@PathVariable String name) {
        return "hello " + name;
    }

    @GetMapping("/hello")
    public String helloWithQuery(String name) {
        return name;
    }

    @PostMapping("/person")
    public Person person(@RequestBody @Validated Person person) {
        return person;
    }

    @PostMapping("/person/{path}")
    public Person personWithouBody(@Validated Person person) {
        return person;
    }

}
