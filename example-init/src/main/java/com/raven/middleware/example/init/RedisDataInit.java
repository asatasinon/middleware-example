package com.raven.middleware.example.init;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.raven.middleware.example.api.IDataInit;
import com.raven.middleware.example.database.constant.RedisKeyConstant;
import com.raven.middleware.example.database.service.RedisService;
import com.raven.middleware.example.util.JacksonUtils;

/**
 * @author raven
 * @date 2024/6/26 18:10
 * @description
 */

@Slf4j
@Component
public class RedisDataInit implements IDataInit {

    @Autowired
    private RedisService redisService;

    @Value("${config.computerSkuPath:data/computer-sku.json}")
    private String computerSkuPath;

    @Value("${config.redisDataInitScriptPath:scripts/redis-data-init.lua}")
    private String redisDataInitScriptPath;

    @Override
    public boolean checkInitialized() {
        boolean setNxSuccess = redisService.setNx(RedisKeyConstant.DATA_HAS_INITIALIZED, "1");
        log.info("RedisDataInit preInitData hasInit:{}", !setNxSuccess);
        return setNxSuccess;
    }

    @Override
    public void preInitData() throws Exception {
        log.info("RedisDataInit preInitData");
    }

    @Override
    public void postInitData() throws Exception {
        String redisDataInitScript = getRedisDataInitScript();
        Boolean scriptResult = redisService.eval(redisDataInitScript);
        log.info("RedisDataInit postInitData scriptResult:{}", scriptResult);

        // 初始化商品sku信息
//        List<Map> skuList = getSkuList();
//        redisService.executePipelined(
//            skuList.stream().map(sku -> String.format(RedisKeyConstant.REDIS_SKU_KEY, sku.get("id").toString()))
//                .collect(Collectors.toList()),
//            skuList);

        log.info("RedisDataInit postInitData");

    }

    @Override
    public void afterInitData() throws Exception {
        log.info("RedisDataInit afterInitData");
    }

    public List<Map> getSkuList() {
        try {
            ClassPathResource resource = new ClassPathResource(computerSkuPath);
            return JacksonUtils.json2list(resource.getInputStream(), Map.class);

        } catch (Exception e) {
            log.error("readConfig error", e);
        }
        return new ArrayList<>();
    }

    public String getRedisDataInitScript() {
        try {
            ClassPathResource resource = new ClassPathResource(redisDataInitScriptPath);
            return new String(resource.getInputStream().readAllBytes());

        } catch (Exception e) {
            log.error("readConfig error", e);
        }
        return "";
    }
}
