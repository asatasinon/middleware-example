package com.raven.middleware.example.api;

/**
 * @author raven
 * @date 2024/6/24 18:50
 * @description
 */
public interface IDataInit {
    void preInitData() throws Exception;
    void postInitData() throws Exception;
    void afterInitData() throws Exception;
}
