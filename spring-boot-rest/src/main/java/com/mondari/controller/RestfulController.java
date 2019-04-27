package com.mondari.controller;

import com.mondari.model.Person;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestfulController {

    @GetMapping("/hello/{name}/{old}")
    public Person helloWithPath(@PathVariable String name, @PathVariable Integer old) {
        return new Person(name, old);
    }

    @GetMapping("/hello")
    public Person helloWithQuery(@RequestParam(defaultValue = "world") String name, @RequestParam(defaultValue = "0") Integer old) {
        return new Person(name, old);
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
