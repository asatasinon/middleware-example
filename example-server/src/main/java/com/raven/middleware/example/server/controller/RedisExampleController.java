package com.raven.middleware.example.server.controller;

import java.text.ParseException;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.server.service.RedisExampleService;

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
     * 全局计数器
     * 请求方式: GET
     * 请求参数: counterId
     * 返回值: 计数器值
     * 样例: /redis/counter/1001
     */
    @GetMapping("/counter/{counterId}")
    public String getCounter(@PathVariable("counterId") String counterId) {
        return redisExampleService.getCounter(counterId);
    }

    /**
     * 点赞
     * 请求方式: GET
     * 请求参数: userId
     * 返回值: 点赞数
     * 样例: /redis/star/1001
     */
    @GetMapping("/star/{userId}")
    public String getStarCount(@PathVariable("userId") String userId) {
        return redisExampleService.getStarCount(userId);
    }


    /**
     * 签到
     * 请求方式: get
     * 请求参数: userId, year
     * 返回值: 签到结果
     * 样例: /redis/sign-in?userId=1001&year=2024
     */
    @GetMapping ("/sign-in")
    public String getSignIn(@RequestParam String userId, @RequestParam String year) {
        return redisExampleService.getSignIn(userId, year);
    }

    /**
     * 签到
     * 请求方式: POST
     * 请求参数: userId, date
     * 返回值: 签到结果
     * 样例: /redis/sign-in
     * 请求体: {"userId":"1001","date":"2024-06-21"}
     */
    @PostMapping("/sign-in")
    public String signIn(@RequestBody Map<String, String> data) throws ParseException {
        String userId = data.get("userId");
        LocalDate date = LocalDate.parse(data.get("date"));
        int year = date.getYear();
        int day = date.getDayOfYear();
        return redisExampleService.signIn(userId, String.valueOf(year), day);
    }

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
     * 更新用户信息
     * 请求方式: POST
     * 请求参数: userId, userInfo
     * 返回值: 更新后的用户信息
     * 样例: /redis/user-info
     * 请求体: {"userId":"1001","userInfo":{"name":"张三","age":18}}
     */
    @PostMapping("/user-info")
    public String updateUserInfo(@RequestBody Map<String, Object> data) {
        return redisExampleService.updateUserInfo(data);
    }


    /**
     * 获取用户关注信息
     * 请求方式: GET
     * 请求参数: userId
     * 返回值: 关注信息
     * 样例: /redis/user-follow/1001
     */
    @GetMapping("/user-follow/{userId}")
    public String getUserFollow(@PathVariable("userId") String userId) {
        return redisExampleService.getUserFollow(userId);
    }

    /**
     * 关注用户
     * 请求方式: Post
     * 请求参数: userId, followUserId
     * 返回值: 关注信息
     * 样例: /redis/user-follow/1001
     * 请求体: {"userId": "1001", "followUserId": "1002"}
     */
    @PostMapping("/user-follow")
    public String followUser(@RequestBody Map<String, String> data) {
        return redisExampleService.followUser(data.get("userId"), data.get("followUserId"));
    }

    /**
     * 获取共同关注的用户
     * 请求方式: post
     * 请求参数: userIds
     * 返回值: 共同关注的用户
     * 样例: /redis/common-follow
     * 请求体: {"userIds": ["1001", "1002"]}
     */
    @PostMapping("/common-follow")
    public String getCommonFollow(@RequestBody Map<String, Object> data) {
        return redisExampleService.getCommonFollow(((List<String>) data.get("userIds")).toArray(new String[0]));
    }

    /**
     * 是否互相关注
     * 请求方式: get
     * 请求参数: userId1, userId2
     * 返回值: 是否互相关注
     * 样例: /redis/is-mutual-follow?userId1=1001&userId2=1002
     */
    @GetMapping("/is-mutual-follow")
    public String isMutualFollow(@RequestParam String userId1, @RequestParam String userId2) {
        return redisExampleService.isMutualFollow(userId1, userId2);
    }

    /**
     * 可能认识的人
     * 请求方式: get
     * 请求参数: userId
     * 返回值: 可能认识的人
     * 样例: /redis/may-know?userId=1001
     */
    @GetMapping("/may-know")
    public String mayKnow(@RequestParam String userId) {
        return redisExampleService.mayKnow(userId);
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
     * 请求参数:  date, newsId, score
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


}
