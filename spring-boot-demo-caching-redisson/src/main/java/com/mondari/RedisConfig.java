package com.mondari;

import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class RedisConfig {

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>(1);
        // 创建一个名称为"books"的缓存，过期时间ttl为10s，同时最长空闲时maxIdleTime为12分钟。
        config.put("books", new CacheConfig(10 * 1000, 12 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config, new JsonJacksonCodec());
    }

}
