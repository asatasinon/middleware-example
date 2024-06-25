package com.raven.middleware.example.init;

import org.springframework.stereotype.Component;

import com.raven.middleware.example.api.IDataInit;

/**
 * @author raven
 * @date 2024/6/24 18:48
 * @description
 */


@Component
public class RedisDataInit implements IDataInit {
    @Override
    public void preInitData() throws Exception {

    }

    @Override
    public void postInitData() throws Exception {


    }

    @Override
    public void afterInitData() throws Exception {

    }


}
