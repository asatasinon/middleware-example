package com.raven.middleware.example.server.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.database.constant.RedisKeyConstant;
import com.raven.middleware.example.util.JacksonUtils;

/**
 * @author raven
 * @date 2024/7/2 16:29
 * @description
 */
@Slf4j
@Service
public class RedisExampleService {
    public static final int IP_LIMIT_COUNT = 5;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public String getShoppingCart(String userId) {
        log.info("userId={}", userId);
        Object o = redisTemplate.opsForHash()
            .entries(RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_SHOPPING_CART_KEY, userId));
        return "key=shopping:cart:1001, value=" + JacksonUtils.obj2json(o);
    }

    public String updateShoppingCart(Map<String, Object> data) {
        log.info("data={}", data);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_SHOPPING_CART_KEY, data.get("userId"));
        Map<?, ?> shoppingCart = (Map<?, ?>) data.get("shoppingCart");

        Map<String, String> collect = shoppingCart.entrySet().stream()
            .collect(Collectors.toMap(
                entry -> entry.getKey().toString(),
                entry -> entry.getValue().toString()
            ));

        redisTemplate.opsForHash().putAll(key, collect);

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);

        return JacksonUtils.obj2json(entries);
    }

    public String getIpLimit(String ip) {
        log.info("ip: {}", ip);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_IP_LIMIT_KEY, ip);
        long now = System.currentTimeMillis();
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(now));
        // 限制每分钟访问次数
        Long count = redisTemplate.opsForZSet().count(key, now - 60 * 1000, now);
        if (count != null && count >= IP_LIMIT_COUNT) {
            return "当前时间为:[" + formatDate + "], "
                   + "ip:[" + ip + "]已达访问次数上限, "
                   + "上限为:[" + IP_LIMIT_COUNT + "次/分钟]";
        }

        // 删除历史访问记录
        redisTemplate.opsForZSet().removeRangeByScore(key, 0, now - 60 * 1000 - 1);

        // 添加访问记录
        Boolean add = redisTemplate.opsForZSet().add(key, String.valueOf(now), now);

        return "访问:" + add + ", 当前访问次数: " + (count == null ? 1 : count + 1) + "次/分钟";
    }

    public String getRank(String userId) {
        log.info("userId: {}", userId);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_RANK_KEY, "1");
        log.info(JacksonUtils.obj2json(redisTemplate.opsForZSet().rangeWithScores(key, 0, -1)));

        Long total = redisTemplate.opsForZSet().zCard(key);

        Long rank = redisTemplate.opsForZSet().reverseRank(key, userId);

        Double score = redisTemplate.opsForZSet().score(key, userId);

        String result = rank == null ? "未上榜" : "排名: " + (rank + 1);

        result += ", 您当前的分数为: " + score + ", 总榜人数: " + total;

        return result;
    }

    public String getUserInfo(String userId) {
        log.info("userId: {}", userId);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_INFO_KEY, userId);
        return JacksonUtils.obj2json(redisTemplate.opsForHash().entries(key));
    }

    public String getHotNews(String date, int count) {
        log.info("date: {}, count: {}", date, count);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_HOT_NEWS_KEY, date);
        return JacksonUtils.obj2json(redisTemplate.opsForZSet().reverseRange(key, 0, count - 1));
    }

    public String updateHotNews(String date,  String news, Double score) {
        log.info("date: {}, news: {}", date, news);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_HOT_NEWS_KEY, date);
        Double oldScore = redisTemplate.opsForZSet().score(key, news);
        Double newScore = redisTemplate.opsForZSet().incrementScore(key, news, score);

        return "新闻: " + news + ", 旧分数: " + oldScore + ", 新分数: " + newScore;
    }



}
