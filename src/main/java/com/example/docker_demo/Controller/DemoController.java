package com.example.docker_demo.Controller;

import com.example.docker_demo.Entity.UserEntity;
import com.example.docker_demo.Repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    public DemoController(UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello Docker";
    }

    @GetMapping("/version2")
    public String hello2() {
        return "Hello Docker Version 2";
    }

    @PostMapping("/users/{name}")
    public UserEntity createUser(@PathVariable String name) {
        return userRepository.save(new UserEntity(name));
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/cache/{key}/{value}")
    public String setCache(@PathVariable String key,
                           @PathVariable String value) {

        redisTemplate.opsForValue().set(key, value);

        return "Saved to Redis";
    }

    @GetMapping("/cache/{key}")
    public String getCache(@PathVariable String key) {

        return redisTemplate.opsForValue().get(key);
    }
}
