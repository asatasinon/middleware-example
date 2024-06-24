package com.raven.middleware.example.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Resource;

import com.raven.middleware.example.server.interceptor.WebHandlerInterceptor;

/**
 * @author raven
 * @date 2024/6/21 11:29
 * @description
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private WebHandlerInterceptor webHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webHandlerInterceptor).addPathPatterns("/**").excludePathPatterns("/login/**");
    }
}
