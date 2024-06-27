package com.raven.middleware.example.database.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.raven.middleware.example.database.service.RedisService;

/**
 * @author raven
 * @date 2024/6/24 16:19
 * @description
 */
@Configuration
public class RedisConfiguration {

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        // 根据实际情况配置连接工厂，例如使用LettuceConnectionFactory或JedisConnectionFactory
//        // 默认使用 LettuceConnectionFactory, 这里不用创建 bean
//        return new LettuceConnectionFactory();
//    }


    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(RedisService.class)
    public RedisService redisService() {
        return new RedisService();
    }
}
