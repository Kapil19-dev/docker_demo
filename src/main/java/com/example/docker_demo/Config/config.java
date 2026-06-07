package com.example.docker_demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class config {

    @Bean
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory factory) {

        return new StringRedisTemplate(factory);
    }
}
