package com.raven.middleware.example.database.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.Getter;

/**
 * @author raven
 * @date 2024/6/21 11:26
 * @description
 */

@Component
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

    public Boolean setNx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
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

    public void lPush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    public void lSet(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    public void lRem(String key, long count, String value) {
        redisTemplate.opsForList().remove(key, count, value);
    }

    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    public void lInsert(String key, long index, String pivot, String value) {
        redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    public void lPushAll(String key, String... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }


    public void executePipelined(List<String> keys, List<Object> values) {
        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {

            for (int i = 0; i < keys.size(); i++) {
                if (values.get(i) instanceof String) {
                    connection.stringCommands()
                        .set(keys.get(i).getBytes(StandardCharsets.UTF_8),
                            ((String) values.get(i)).getBytes(StandardCharsets.UTF_8));
                } else if (values.get(i) instanceof Map<?, ?>) {
                    connection.hashCommands()
                        .hMSet(keys.get(i).getBytes(StandardCharsets.UTF_8),
                            ((Map<String, String>) values.get(i)).entrySet().stream().map(entry ->
                                    new MapEntry(entry.getKey().getBytes(StandardCharsets.UTF_8),
                                        entry.getValue().getBytes(StandardCharsets.UTF_8)))
                                .collect(Collectors.toMap(MapEntry::getKey, MapEntry::getValue)));

                } else if (values.get(i) instanceof Set<?>) {
                    connection.setCommands()
                        .sAdd(keys.get(i).getBytes(StandardCharsets.UTF_8),
                            ((Set<String>) values.get(i)).stream().map(String::getBytes).toArray(byte[][]::new));
                } else if (values.get(i) instanceof List<?>) {
                    connection.listCommands()
                        .lPush(keys.get(i).getBytes(StandardCharsets.UTF_8),
                            ((List<String>) values.get(i)).stream().map(String::getBytes).toArray(byte[][]::new));
                }
            }
            return null;
        });
    }

    @Getter
    public static class MapEntry {
        private final byte[] key;
        private final byte[] value;

        public MapEntry(byte[] key, byte[] value) {
            this.key = key;
            this.value = value;
        }

    }
}
