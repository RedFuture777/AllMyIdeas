package org.example.controller;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/limit")
public class LimitingController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @GetMapping("/transfer")
    public void transfer(){
        redisTemplate.opsForValue().set("k1", "v1");
    }

}
