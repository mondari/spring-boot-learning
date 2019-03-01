package com.mondari.springbootredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RedisController {
    private
    StringRedisTemplate redisTemplate;

    @Autowired
    public RedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * set key value
     *
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/value")
    public String setValue(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        return valueOperations.get(key);
    }

    /**
     * get key
     *
     * @param key
     * @return
     */
    @GetMapping("/value")
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * hset hname hkey hvalue
     *
     * @param hashName
     * @param hashKey
     * @param hashValue
     * @return
     */
    @PostMapping("/hash")
    public Map<Object, Object> setHash(String hashName, String hashKey, String hashValue) {
        redisTemplate.opsForHash().put(hashName, hashKey, hashValue);
        return redisTemplate.opsForHash().entries(hashName);
    }

    /**
     * hgetall
     *
     * @param hashName
     * @return
     */
    @GetMapping("/hash")
    public Map<Object, Object> getHash(String hashName) {
        return redisTemplate.opsForHash().entries(hashName);
    }

    /**
     * lpush key value ...
     *
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/list")
    public List<String> setList(String key, String value) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, value);
        return listOperations.range(key, 0, -1);
    }

    /**
     * lrange key 0 -1
     *
     * @param key
     * @return
     */
    @GetMapping("/list")
    public List<String> getList(String key) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }

}
