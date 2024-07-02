-- 初始化全局计数器, redis 中的计数器是字符串, 可以直接进行加减操作
-- key: counter:${id} value: ${count}
-- 使用 incrby counter:${id} 可以进行自增操作
-- 使用 decrby counter:${id} 可以进行自减操作
-- 样例
-- 设置语句: set counter:1001 0
-- 自增操作: incrby counter:1001
-- 自减操作: decrby counter:1001
-- 查询计数: get counter:1001
redis.call('set', 'counter:1001', 0)

-- 初始化点赞数, redis 中的点赞数是字符串, 可以直接进行加减操作
-- key: star:${id} value: ${count}
-- 使用 incrby star:${id} 可以进行自增操作
-- 使用 decrby star:${id} 可以进行自减操作
-- 样例:
-- 设置语句: set star:1001 0
-- 自增操作: incrby star:1001
-- 自减操作: decrby star:1001
-- 查询点赞数: get star:1001
redis.call('set', 'star:1001', 0)

-- 初始化登录签到, redis 中的签到是字符串, 使用 bit 操作进行签到
-- key: sign:${year}:${userId} value: ''
-- 使用 setbit sign:${year}:${userId} ${day} 1 可以进行签到操作
-- 使用 getbit sign:${year}:${userId} ${day} 可以查询签到状态
-- 样例:
-- 签到: setbit sign:2021:1001 140 1
-- 查询第140天的签到状态: getbit sign:2024:1001 140
-- 查询签到次数: bitcount sign:2024:1001 1 366
redis.call('setbit', 'sign:2024:1001', 140, 1)
redis.call('setbit', 'sign:2024:1001', 141, 1)
redis.call('setbit', 'sign:2024:1001', 142, 1)
redis.call('setbit', 'sign:2024:1001', 143, 1)
redis.call('setbit', 'sign:2024:1001', 144, 1)
redis.call('setbit', 'sign:2024:1001', 145, 1)
redis.call('setbit', 'sign:2024:1001', 146, 1)
redis.call('setbit', 'sign:2024:1001', 147, 1)
redis.call('setbit', 'sign:2024:1001', 148, 1)
redis.call('setbit', 'sign:2024:1001', 149, 1)
redis.call('setbit', 'sign:2024:1001', 160, 1)
redis.call('setbit', 'sign:2024:1001', 161, 1)
redis.call('setbit', 'sign:2024:1001', 162, 1)
redis.call('setbit', 'sign:2024:1001', 163, 1)
redis.call('setbit', 'sign:2024:1001', 164, 1)
redis.call('setbit', 'sign:2024:1001', 165, 1)
redis.call('setbit', 'sign:2024:1001', 166, 1)
redis.call('setbit', 'sign:2024:1001', 167, 1)
redis.call('setbit', 'sign:2024:1001', 168, 1)
redis.call('setbit', 'sign:2024:1001', 169, 1)

-- 初始化购物车, redis 中的购物车是 hash, 可以直接进行增删操作
-- key: shopping:cart:${userId} field: ${productId} value: ${count}
-- 使用 hset shopping:cart:${userId} ${productId} ${count} 可以进行添加商品到购物车
-- 使用 hmset shopping:cart:${userId} ${productId1} ${count1} ${productId2} ${count2} 可以批量添加商品到购物车
-- 使用 hincrby shopping:cart:${userId} ${productId} ${count} 可以增加或减少购物车中的商品数量
-- 使用 hdel shopping:cart:${userId} ${productId} [${productId} ...] 可以删除购物车中的一个或多个商品
-- 使用 hget shopping:cart:${userId} ${productId} 可以查询购物车中的商品数量
-- 使用 hgetall shopping:cart:${userId} 可以查询购物车中的所有商品以及数量
-- 使用 hlen shopping:cart:${userId} 可以查询购物车中的商品数量
-- 样例:
-- 添加商品到购物车: hset shopping:cart:1001 10 1
-- 查询商品 10 的数量: hget shopping:cart:1001 10
redis.call('hset', 'shopping:cart:1001', 1, 1)
redis.call('hset', 'shopping:cart:1002', 2, 1)
redis.call('hset', 'shopping:cart:1003', 3, 1)

-- 初始化用户信息, redis 中的用户信息是 hash, 可以直接进行增删改查操作
-- key: user:${userId} field: ${field} value: ${value}
-- 使用 hset user:${userId} ${field} ${value} 可以设置用户信息
-- 使用 hmset user:${userId} ${field1} ${value1} ${field2} ${value2} 可以批量设置单个用户的多个信息
-- 使用 hget user:${userId} ${field} 可以查询用户信息
-- 使用 hdel user:${userId} ${field} 可以删除用户信息
-- 使用 hgetall user:${userId} 可以查询用户所有信息
-- 样例:
-- 设置用户信息: hmset user:1001 name '张三' age 18 address '广州'
-- 查询用户 1001 的姓名: hget user:1001 name
redis.call('hmset', 'user:1001', 'name', '张三', 'age', 18, 'address', '广州')
redis.call('hmset', 'user:1002', 'name', '李四', 'age', 20, 'address', '深圳')
redis.call('hmset', 'user:1003', 'name', '王五', 'age', 22, 'address', '上海')
redis.call('hmset', 'user:1004', 'name', '赵六', 'age', 24, 'address', '北京')
redis.call('hmset', 'user:1005', 'name', '孙七', 'age', 26, 'address', '杭州')
redis.call('hmset', 'user:1006', 'name', '周八', 'age', 28, 'address', '南京')
redis.call('hmset', 'user:1007', 'name', '吴九', 'age', 30, 'address', '武汉')
redis.call('hmset', 'user:1008', 'name', '郑十', 'age', 32, 'address', '成都')
redis.call('hmset', 'user:1009', 'name', '钱十一', 'age', 34, 'address', '重庆')
redis.call('hmset', 'user:1010', 'name', '孔十二', 'age', 36, 'address', '西安')

-- 初始化商品信息, redis 中的商品信息是 hash, 可以直接进行增删改查操作
-- key: sku:${productId} field: ${field} value: ${value}
-- 使用 hset sku:${productId} ${field} ${value} 可以设置商品信息
-- 使用 hmset sku:${productId} ${field1} ${value1} ${field2} ${value2} 可以批量设置单个商品的多个信息
-- 使用 hget sku:${productId} ${field} 可以查询商品信息
-- 使用 hdel sku:${productId} ${field} 可以删除商品信息
-- 使用 hgetall sku:${productId} 可以查询商品所有信息
-- 样例: redis 语句: hmset sku:1001 name '商品1' price 100 stock 100
-- 使用 hget sku:1001 name 可以查询商品 1001 的名称
local products = {
    { id = 1, name = '商品1', price = 100, stock = 100 },
    { id = 2, name = '商品2', price = 200, stock = 200 },
    { id = 3, name = '商品3', price = 300, stock = 300 },
    { id = 4, name = '商品4', price = 400, stock = 400 },
    { id = 5, name = '商品5', price = 500, stock = 500 },
    { id = 6, name = '商品6', price = 600, stock = 600 },
    { id = 7, name = '商品7', price = 700, stock = 700 },
    { id = 8, name = '商品8', price = 800, stock = 800 },
    { id = 9, name = '商品9', price = 900, stock = 900 },
    { id = 10, name = '商品10', price = 1000, stock = 1000 },
    { id = 11, name = '商品11', price = 1100, stock = 1100 },
    { id = 12, name = '商品12', price = 1200, stock = 1200 },
    { id = 13, name = '商品13', price = 1300, stock = 1300 },
    { id = 14, name = '商品14', price = 1400, stock = 1400 },
    { id = 15, name = '商品15', price = 1500, stock = 1500 },
    { id = 16, name = '商品16', price = 1600, stock = 1600 },
    { id = 17, name = '商品17', price = 1700, stock = 1700 },
    { id = 18, name = '商品18', price = 1800, stock = 1800 },
    { id = 19, name = '商品19', price = 1900, stock = 1900 },
    { id = 20, name = '商品20', price = 2000, stock = 2000 }
}

for i = 1, #products do
    local product = products[i]
    local productId = product.id
    local skuKey = 'product:sku:' .. productId

    for f, v in pairs(product) do
        -- Skip the id field to avoid duplication, assuming it's not needed as a hash field.
        if f ~= 'id' then
            redis.call('hset', skuKey, f, tostring(v))
        end
    end
end

-- 初始化用户关注, redis 中的用户关注是集合, 可以直接进行增删查操作
-- key: user:follow:${userId} value: ${followUserId}
-- 使用 sadd user:follow:${userId} ${followUserId} 可以添加关注
-- 使用 srem user:follow:${userId} ${followUserId} 可以取消关注
-- 使用 smembers user:follow:${userId} 可以查询关注的用户
-- 使用 scard user:follow:${userId} 可以查询关注的用户数量
-- 使用 sismember user:follow:${userId} ${followUserId} 可以查询用户userId是否关注followUserId
-- 使用 sinter user:follow:${userId1} user:follow:${userId2} 可以查询两个用户的共同关注
-- 样例:
-- 添加关注:
-- sadd user:follow:1001 1002 1003 1004 1005 1006 1007
-- sadd user:follow:1002 1001 1003 1004 1005 1008
-- sadd user:follow:1003 1001 1004 1005 1008 1009 1010
-- sadd user:follow:1004 1001 1003 1005 1008 1009 1010
-- 查询关注的用户: smembers user:follow:1001
-- 查询关注的用户数量: scard user:follow:1001
-- 查询用户1001是否关注用户1002: sismember user:follow:1001 1002
-- 查询用户1001和1002的共同关注: sinter user:follow:1001 user:follow:1002
-- 查询用户1001和1002是否互相关注: sismember user:follow:1001 1002 && sismember user:follow:1002 1001
-- 查询用户1001是否关注的用户中是否关注了1005: smembers user:follow:1001 && sismember user:follow:${userFollowId} 1005
-- 可能认识的人(1001不认识, 但 1001 关注的人认识 = 1001 可能认识):
-- sdiffstore maybe:know:user:1001 user:follow:1001 user:follow:1002 user:follow:1003 user:follow:1004
redis.call('sadd', 'user:follow:1001', 1002, 1003, 1004)
redis.call('sadd', 'user:follow:1002', 1001, 1003, 1004, 1005, 1006)
redis.call('sadd', 'user:follow:1003', 1001, 1004, 1005, 1007, 1008, 1009)
redis.call('sadd', 'user:follow:1004', 1001, 1003, 1005, 1008, 1009, 1010)

-- 初始化商品标签, redis 中的商品标签是集合, 可以直接进行增删查操作
-- key: product:tag:${productId} value: ${tag}
-- 使用 sadd product:tag:${productId} ${tag} 可以添加商品标签
-- 使用 srem product:tag:${productId} ${tag} 可以删除商品标签
-- 使用 smembers product:tag:${productId} 可以查询商品标签
-- 使用 scard product:tag:${productId} 可以查询商品标签数量
-- 使用 sismember product:tag:${productId} ${tag} 可以查询商品是否有某个标签
-- 使用 sinter product:tag:${productId1} product:tag:${productId2} 可以查询两个商品的共同标签
-- 样例:
-- 添加商品标签:
-- sadd product:tag:1001 '标签1' '标签2' '标签3' '标签4' '标签5'
-- sadd product:tag:1002 '标签1' '标签2' '标签3' '标签6' '标签7'
-- sadd product:tag:1003 '标签1' '标签2' '标签3' '标签8' '标签9'
-- 查询商品1001标签: smembers product:tag:1001
-- 查询商品1001标签数量: scard product:tag:1001
-- 查询商品1001是否有标签'标签1': sismember product:tag:1001 '标签1'
-- 查询商品1001和1002的共同标签: sinter product:tag:1001 product:tag:1002
redis.call('sadd', 'product:tag:1', '标签1', '标签2', '标签3', '标签4', '标签5')
redis.call('sadd', 'product:tag:2', '标签1', '标签2', '标签3', '标签6', '标签7')
redis.call('sadd', 'product:tag:3', '标签1', '标签2', '标签3', '标签8', '标签9')

-- 不重复抽奖, redis 中的抽奖是集合, 可以直接进行增删查操作
-- key: lottery:${id} value: ${userId}
-- 使用 sadd lottery:${id} ${userId} 可以添加抽奖用户
-- 使用 spop lottery:${id} 可以随机抽取一个用户(获取后会从set集合中删除)
-- 样例:
-- 添加抽奖用户: sadd lottery:1 1001 1002 1003 1004 1005 1006 1007 1008 1009 1010
-- 随机抽取一个用户: spop lottery:1
redis.call('sadd', 'lottery:1', 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010)

-- 初始化排行榜, redis 中的排行榜是有序集合, 可以直接进行增删查操作
-- key: rank:${id} value: ${userId} score: ${score}
-- 使用 zadd rank:${id} ${score} ${userId} 可以添加用户到排行榜
-- 使用 zrem rank:${id} ${userId} 可以删除用户
-- 使用 zrange rank:${id} ${start} ${stop} 可以查询排行榜
-- 使用 zrevrange rank:${id} ${start} ${stop} 可以查询倒序排行榜
-- 使用 zrangebyscore rank:${id} ${min} ${max} 可以查询指定分数范围的排行榜
-- 使用 zrank rank:${id} ${userId} 可以查询用户排名
-- 使用 zcard rank:${id} 可以查询排行榜用户数量
-- 使用 zscore rank:${id} ${userId} 可以查询用户分数
-- 使用 zcount rank:${id} ${min} ${max} 可以查询指定分数范围的用户数量
-- 样例:
-- 添加用户到排行榜: zadd rank:1 100 1001 90 1002 80 1003 70 1004 60 1005 50 1006 40 1007 30 1008 20 1009 10 1010
-- 查询排行榜: zrange rank:1 0 -1
-- 查询倒序排行榜: zrevrange rank:1 0 -1
-- 查询前三名: zrange rank:1 0 2
-- 查询后三名: zrevrange rank:1 0 2
-- 查询分数大于等于 50 的用户: zrangebyscore rank:1 50 +inf
-- 查询分数小于等于 50 的用户: zrangebyscore rank:1 -inf 50
-- 查询用户1001的排名: zrank rank:1 1001
-- 查询排行榜用户数量: zcard rank:1
-- 查询用户1001的分数: zscore rank:1 1001
-- 查询分数大于等于 50 的用户数量: zcount rank:1 50 +inf
-- 查询分数小于等于 50 的用户数量: zcount rank:1 -inf 50
redis.call('zadd', 'rank:1', 100, 1001, 90, 1002, 80, 1003, 70, 1004, 60, 1005, 50, 1006, 40, 1007, 30, 1008, 20, 1009, 10, 1010)

-- 初始化 IP 访问限制, redis 中的 IP 访问限制是有序集合, 可以直接进行增删查操作
-- key: limit:ip:${ip} value: ${timestamp} score: ${timestamp}
-- 使用 zadd limit:ip:${ip} ${timestamp} ${timestamp} 可以添加访问记录
-- 使用 zremrangebyscore limit:ip:${ip} -inf (${timestamp} - ${interval}) 可以删除过期访问记录
-- 使用 zcount limit:ip:${ip} -inf +inf 可以查询访问记录数量
-- 使用 zrevrange limit:ip:${ip} 0 -1 可以查询访问记录
-- 使用 zrangebyscore limit:ip:${ip} ${timestamp} +inf 可以查询指定时间之后的访问记录
-- 样例:
-- 添加访问记录: zadd limit:ip:127.0.0.1 1719908421900 1719908421900 1719908421910 1719908421910 1719908421920 1719908421920 1719908421930 1719908421930 1719908421940 1719908421940 1719908421950 1719908421950
-- 删除过期访问记录: zremrangebyscore limit:ip:127.0.0.1 -inf 1719908421900
-- 查询访问记录数量: zcount limit:ip:127.0.0.1 -inf +inf
-- 查询访问记录: zrange limit:ip:127.0.0.1 0 -1
-- 查询指定时间之后的访问记录: zrangebyscore limit:ip:127.0.0.1 1719908421920  +inf
redis.call('zadd', 'limit:ip:127.0.0.1', 1719908421900, 1719908421900, 1719908421910, 1719908421910, 1719908421920, 1719908421920, 1719908421930, 1719908421930, 1719908421940, 1719908421940, 1719908421950, 1719908421950)

-- 初始化热点新闻, redis 中的热点新闻是有序集合, 可以直接进行增删查操作
-- key: hot:news:${date} value: ${newsId} score: ${score}
-- 使用 zadd hot:news:${date} ${score} ${newsId} 可以添加新闻
-- 使用 zrem hot:news:${date} ${newsId} 可以删除新闻
-- 使用 zrevrange hot:news:${date} 0 -1 可以查询热点新闻
-- 使用 zincrby hot:news:${date} ${score} ${newsId} 可以增加新闻热度
-- 样例:
-- 添加新闻: zadd hot:news:20240701 10000 本台报道-本台被淹 90 中欧班列“跑”出加速度 80 张志杰队医因未经裁判允许未进场 70 3个月8名厅官被查-唐山官场巨震 60 网红夹包哥遇害因跳舞时和嫌犯对视 50 中欧班列“跑”出加速度 40 警方通报业主砍断高空工人安全绳 30 困在网贷里的年轻人 20 985大学排名一览表 10 本月你的工资或有多项调整
-- 查询热点新闻: zrevrange hot:news:20240701 0 -1
-- 增加新闻热度: zincrby hot:news:20240701 10 本月你的工资或有多项调整
redis.call('zadd', 'hot:news:20240701', 10000, '本台报道-本台被淹', 90, '中欧班列“跑”出加速度', 80, '张志杰队医因未经裁判允许未进场', 70, '3个月8名厅官被查-唐山官场巨震', 60, '网红夹包哥遇害因跳舞时和嫌犯对视', 50, '中欧班列“跑”出加速度', 40, '警方通报业主砍断高空工人安全绳', 30, '困在网贷里的年轻人', 20, '985大学排名一览表', 10, '本月你的工资或有多项调整')


return true
