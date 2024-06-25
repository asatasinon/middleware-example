package com.raven.middleware.example.server.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @author raven
 * @date 2024/6/21 11:26
 * @description
 */

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, java.util.concurrent.TimeUnit.SECONDS);
    }

    public Boolean expireAt(String key, long timestamp) {
        return redisTemplate.expireAt(key, new java.util.Date(timestamp));
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, java.util.concurrent.TimeUnit.SECONDS);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void hSet(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void hPutAll(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void hPut(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public Boolean hPutIfAbsent(String key, String field, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    public void hDel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    public void sSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public void setRemove(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);

    }

}
