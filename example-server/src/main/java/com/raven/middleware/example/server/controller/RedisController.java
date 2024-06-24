package com.raven.middleware.example.server.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.server.service.RedisService;

/**
 * @author raven
 * @date 2024/6/21 10:16
 * @description
 */

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {


    @Resource
    private RedisService redisService;

    @GetMapping("/shoppingCart")
    public String shoppingCart() {

        redisService.set("shoppingCart", "shoppingCart");
        return "success";
    }


}
