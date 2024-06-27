package com.raven.middleware.example.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.api.IDataInit;
import com.raven.middleware.example.database.service.RedisService;

/**
 * @author raven
 * @date 2024/6/26 18:10
 * @description
 */

@Slf4j
@Component
public class RedisDataInit implements IDataInit {

    // 全局计数器
    public static final String DATA_HAS_INITIALIZED = "data:has:initialized";

    // 全局计数器
    public static final String REDIS_GLOBAL_COUNTER_KEY = "counter:001";

    // 点赞数
    public static final String REDIS_STAR_COUNT_KEY = "star:001";

    // 登录签到 key
    public static final String REDIS_SIGN_IN_KEY = "sign:2024:001";

    // 缓存 key
    public static final String REDIS_CACHE_KEY = "cache:admin:raven";


    @Autowired
    private RedisService redisService;

    @Override
    public boolean checkInitialized() {
        boolean setNxSuccess = redisService.setNx(DATA_HAS_INITIALIZED, "1");
        log.info("RedisDataInit preInitData hasInit:{}", !setNxSuccess);
        return setNxSuccess;
    }

    @Override
    public void preInitData() throws Exception {
        log.info("RedisDataInit preInitData");
    }

    @Override
    public void postInitData() throws Exception {
        // 初始化全局计数器
        redisService.set(REDIS_GLOBAL_COUNTER_KEY, "0");
        //  初始化点赞数
        redisService.set(REDIS_STAR_COUNT_KEY, "0");
        // 初始化登录签到
        redisService.set(REDIS_SIGN_IN_KEY, "");
        // 初始化缓存
        redisService.set(REDIS_CACHE_KEY, "id=001");

        log.info("RedisDataInit postInitData");
    }
    @Override
    public void afterInitData() throws Exception {
        log.info("RedisDataInit afterInitData");
    }
}
