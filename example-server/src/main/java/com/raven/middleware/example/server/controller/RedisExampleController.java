package com.raven.middleware.example.server.controller;

import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.database.constant.RedisKeyConstant;
import com.raven.middleware.example.server.service.RedisExampleService;
import com.raven.middleware.example.util.JacksonUtils;

/**
 * @author raven
 * @date 2024/6/21 10:16
 * @description
 */

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisExampleController {

    @Autowired
    private RedisExampleService redisExampleService;

    /**
     * 购物车查询, 通过userId查询购物车信息
     * 请求方式: GET
     * 请求参数: userId
     * 返回值: 购物车信息
     * 样例: /redis/shopping-cart?userId=1001
     */
    @GetMapping("/shopping-cart")
    public String getShoppingCart(@RequestParam String userId) {
        return redisExampleService.getShoppingCart(userId);
    }

    /**
     * 更新购物车信息
     * 请求方式: POST
     * 请求参数: userId, shoppingCart
     * 返回值: 更新后的购物车信息
     * 样例: /redis/shopping-cart
     * 请求体: {"userId":"1001","shoppingCart":{"1":2,"2":3}}
     */
    @PostMapping("/shopping-cart")
    public String updateShoppingCart(@RequestBody Map<String, Object> data) {
        return redisExampleService.updateShoppingCart(data);
    }

    /**
     * ip访问限制, 暂定上限 5次/分钟
     */
    @GetMapping("/ip-limit")
    public String getIpLimit(HttpServletRequest request) {
        String visitorIp = request.getHeader("X-FORWARDED-FOR");
        if (visitorIp == null || visitorIp.isEmpty() || "unknown".equalsIgnoreCase(visitorIp)) {
            visitorIp = request.getHeader("Proxy-Client-IP");
        }
        if (visitorIp == null || visitorIp.isEmpty() || "unknown".equalsIgnoreCase(visitorIp)) {
            visitorIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (visitorIp == null || visitorIp.isEmpty() || "unknown".equalsIgnoreCase(visitorIp)) {
            visitorIp = request.getRemoteAddr();
        }

        return redisExampleService.getIpLimit(visitorIp);
    }

    /**
     * 排行榜查询
     * 请求方式: GET
     * 请求参数: userId
     * 返回值: 排名
     * 样例: /redis/rank/1001
     */
    @GetMapping("/rank/{userId}")
    public String getRank(@PathVariable("userId") String userId) {
        return redisExampleService.getRank(userId);
    }

    /**
     * 获取用户信息
     * 请求方式: GET
     * 请求参数: userId
     * 返回值: 用户信息
     * 样例: /redis/user-info/1001
     */
    @GetMapping("/user-info/{userId}")
    public String getUserInfo(@PathVariable("userId") String userId) {
        return redisExampleService.getUserInfo(userId);
    }

    /**
     * 热点新闻查询
     * 请求方式: GET
     * 请求参数: count
     * 返回值: 热点新闻
     * 样例: /redis/hot-news?date=20240621&count=5
     */
    @GetMapping("/hot-news")
    public String getHotNews(@RequestParam String date, @RequestParam int count) {
        return redisExampleService.getHotNews(date, count);
    }

    /**
     * 增加新闻热度
     * 请求方式: POST
     * 请求参数: newsId (暂时用的标题代替)
     * 返回值: 热度增加结果
     * 样例: /redis/hot-news
     * 请求体: {"date":"20240621","newsId":"news1","score":1.0}
     */
    @PostMapping("/hot-news")
    public String updateHotNews(@RequestBody Map<String, Object> data) {
        return redisExampleService.updateHotNews(
            data.get("date").toString(),
            data.get("newsId").toString(),
            Double.parseDouble(data.get("score").toString()));
    }

}
