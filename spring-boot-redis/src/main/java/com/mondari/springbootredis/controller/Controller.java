package com.mondari.springbootredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping("/setOne")
    public String setOne(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/getOne")
    public String getOne(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
