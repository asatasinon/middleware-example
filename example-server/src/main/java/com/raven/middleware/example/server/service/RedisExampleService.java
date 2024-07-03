package com.raven.middleware.example.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
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
        log.info("getShoppingCart userId: {}", userId);
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
        log.info("getUserInfo userId: {}", userId);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_INFO_KEY, userId);
        Map<Object, Object> userInfoMap = redisTemplate.opsForHash().entries(key);
        return JacksonUtils.obj2json(userInfoMap);
    }

    public String getHotNews(String date, int count) {
        log.info("date: {}, count: {}", date, count);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_HOT_NEWS_KEY, date);
        Set<Object> rankSet = redisTemplate.opsForZSet().reverseRange(key, 0, count - 1);
        return JacksonUtils.obj2json(rankSet);
    }

    public String updateHotNews(String date, String news, Double score) {
        log.info("date: {}, news: {}", date, news);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_HOT_NEWS_KEY, date);
        // 获取旧分数
        Double oldScore = redisTemplate.opsForZSet().score(key, news);

        // 增加新分数
        Double newScore = redisTemplate.opsForZSet().incrementScore(key, news, score);

        return "新闻: " + news + ", 旧分数: " + oldScore + ", 新分数: " + newScore;
    }

    public String getStarCount(String userId) {
        log.info("getStarCount userId: {}", userId);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_STAR_COUNT_KEY, userId);
        Long count = redisTemplate.opsForValue().increment(key);
        return "点赞用户: " + userId + " 成功, 目前点赞数: " + count;
    }

    public String getUserFollow(String userId) {
        log.info("getUserFollow userId: {}", userId);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_FOLLOW_KEY, userId);
        Set<Object> members = redisTemplate.opsForSet().members(key);
        return "用户: " + userId + ", 关注了用户: " + members;
    }

    public String followUser(String userId, String followUserId) {
        log.info("userId: {}, followUserId: {}", userId, followUserId);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_FOLLOW_KEY, userId);
        Long add = redisTemplate.opsForSet().add(key, followUserId);
        if (add == 0) {
            return "用户: " + userId + " 已经关注了用户: " + followUserId + ", 无需重复关注";
        }
        return "用户: " + userId + ", 关注了用户: " + followUserId + ", 结果: " + add;
    }

    public String getCommonFollow(String... userIds) {
        log.info("userIds: {}", JacksonUtils.obj2json(userIds));
        String[] keys = new String[userIds.length];
        for (int i = 0; i < userIds.length; i++) {
            keys[i] = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_FOLLOW_KEY, userIds[i]);
        }
        Set<Object> intersect = redisTemplate.opsForSet().intersect(Set.of(keys));
        return "用户: " + JacksonUtils.obj2json(userIds) + " 共同关注了用户: " + intersect;
    }

    public String isMutualFollow(String userId1, String userId2) {
        log.info("isMutualFollow userId1: {}, userId2: {}", userId1, userId2);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_FOLLOW_KEY, userId1);
        Boolean member1 = redisTemplate.opsForSet().isMember(key, userId2);

        key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_FOLLOW_KEY, userId2);
        Boolean member2 = redisTemplate.opsForSet().isMember(key, userId1);

        String result = "";


        if (member1 == null || !member1) {
            result += "用户: " + userId1 + " 未关注用户: " + userId2 + ", ";
        } else {
            result += "用户: " + userId1 + " 关注了用户: " + userId2 + ", ";
        }
        if (member2 == null || !member2) {
            result += "用户: " + userId2 + " 未关注用户: " + userId1;
        } else {
            result += "用户: " + userId2 + " 关注了用户: " + userId1;
        }

        if (member1 && member2){
            result = "用户: " + userId1 + " 与用户: " + userId2 + " 互相关注";
            return result;
        }
        return  result;
    }


    public String mayKnow(String userId) {
        log.info("mayKnow userId: {}", userId);
        String key = RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_FOLLOW_KEY, userId);
        Set<Object> members = redisTemplate.opsForSet().members(key);
        ArrayList<Object> keys = new ArrayList<>();
        for (Object member : members) {
            keys.add(RedisKeyConstant.formatKey(RedisKeyConstant.REDIS_USER_FOLLOW_KEY, member.toString()));
        }
        Set<Object> intersect = redisTemplate.opsForSet().intersect(keys);

        assert intersect != null : "intersect is null";

        // 删除自己
        intersect.remove(userId);
        // 删除已经关注的人
        intersect.removeAll(members);

        return "用户: " + userId + " 可能认识的人: " + intersect;
    }

}
