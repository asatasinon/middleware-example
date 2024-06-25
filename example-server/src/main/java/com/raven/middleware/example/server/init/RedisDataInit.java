package com.raven.middleware.example.server.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.api.IDataInit;

/**
 * @author raven
 * @date 2024/6/24 18:48
 * @description
 */


@Slf4j
@Component
public class RedisDataInit implements IDataInit {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void preInitData() throws Exception {

    }

    @Override
    public void postInitData() throws Exception {

        log.info("RedisDataInit postInitData");
    }

    @Override
    public void afterInitData() throws Exception {

    }


}
