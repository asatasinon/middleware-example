package com.raven.middleware.example.database.constant;

/**
 * @author raven
 * @date 2024/6/28 14:54
 * @description
 */

public abstract class RedisKeyConstant {

    // 用于记录数据是否已经初始化, 格式: data:has:initialized
    public static final String DATA_HAS_INITIALIZED = "data:has:initialized";

    // 用于记录全局计数器, 格式: counter:计数器名称
    public static final String REDIS_GLOBAL_COUNTER_KEY = "counter:%s";

    // 用于记录点赞数, 格式: star:用户id
    public static final String REDIS_STAR_COUNT_KEY = "star:%s";

    // 用于记录用户登录签到, 格式: sign:年份:用户id
    public static final String REDIS_SIGN_IN_KEY = "sign:%s:%s";

    // 用于记录用户信息, 格式: user:用户id
    public static final String REDIS_USER_INFO_KEY = "user:%s";

    // 用户关注信息, 格式: user:follow:用户id
    public static final String REDIS_USER_FOLLOW_KEY = "user:follow:%s";

    // 用于记录用户购物车信息, 格式: shopping:cart:用户id,
    // 例如: key 为 shopping:cart:001, field 为 skuId, value 为 sku数量
    public static final String REDIS_SHOPPING_CART_KEY = "shopping:cart:%s";

    // 用于记录商品sku信息, 格式: product:sku:商品id
    public static final String REDIS_SKU_KEY = "product:sku:%s";

    // 用于记录商品tag信息, 格式: product:tag:商品id
    public static final String REDIS_TAG_KEY = "product:tag:%s";

    // 抽奖活动信息, 格式: lottery:活动id
    public static final String REDIS_LOTTERY_KEY = "lottery:%s";

    // 排行榜信息, 格式: rank:排行榜名称
    public static final String REDIS_RANK_KEY = "rank:%s";

}
