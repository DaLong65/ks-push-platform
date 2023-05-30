package com.works.push.service.flowcontrol;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DaLong
 * @date 2023/3/8 18:42
 * <p>
 * 分布式限流器
 */
@Slf4j
@Component
public class DistributedRateLimiter {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 首先，脚本接受了5个参数，分别是：
     * <p>
     * tokens_key: 存储当前令牌数量的Redis键
     * timestamp_key: 存储上一次刷新令牌时间的Redis键
     * rate: 令牌发放速率（每秒发放多少个令牌）
     * capacity: 令牌桶容量（最多能存放多少个令牌）
     * now: 当前时间戳（单位为秒）
     * 其次，脚本根据上述参数计算了当前令牌数量、是否允许当前请求、以及新的令牌数量：
     * <p>
     * fill_time：填满整个令牌桶所需的时间
     * ttl：Redis键的过期时间，等于 fill_time 的两倍
     * last_tokens：上一次剩余的令牌数，如果为空则认为桶是满的，即可存放的令牌数量等于容量
     * last_refreshed：上一次刷新令牌时间戳，如果为空则默认为0
     * delta：两次请求之间的时间间隔
     * filled_tokens：当前请求时刻令牌桶内的令牌数量
     * allowed：当前请求是否允许通过
     * new_tokens：当前请求之后令牌桶中的剩余令牌数量
     * allowed_num：当前请求是否允许通过，允许则为1，不允许则为0
     * 最后，脚本将计算得到的新的令牌数量和允许状态返回。具体实现步骤如下：
     * <p>
     * 如果当前Redis中没有存储过令牌数量，则认为令牌桶是满的
     * 如果当前Redis中没有存储过上一次刷新令牌时间，则默认上一次刷新时间为0
     * 计算两次请求之间的时间间隔
     * 根据时间间隔、令牌发放速率和桶容量，计算当前请求时刻桶中的令牌数量
     * 判断当前请求是否允许通过，如果当前桶中的令牌数不足，则不允许通过，否则允许通过并计算新的令牌数量
     * 将新的令牌数量和允许状态返回，并在Redis中更新令牌数量和刷新时间的值
     */
    private static final String LUA_SCRIPT =
            "local tokens_key = KEYS[1]\n" +
                    "local timestamp_key = KEYS[2]\n" +
                    "local rate = tonumber(ARGV[1])\n" +
                    "local capacity = tonumber(ARGV[2])\n" +
                    "local now = tonumber(ARGV[3])\n" +
                    "local requested = tonumber(ARGV[4])\n" +
                    "local fill_time = capacity/rate\n" +
                    "local ttl = math.floor(fill_time*2)\n" +
                    "local last_tokens = tonumber(redis.call('get', tokens_key))\n" +
                    "if last_tokens == nil then\n" +
                    "    last_tokens = capacity\n" +
                    "end\n" +
                    "local last_refreshed = tonumber(redis.call('get', timestamp_key))\n" +
                    "if last_refreshed == nil then\n" +
                    "    last_refreshed = 0\n" +
                    "end\n" +
                    "local delta = math.max(0, now-last_refreshed)\n" +
                    "local filled_tokens = math.min(capacity, last_tokens+(delta*rate))\n" +
                    "local allowed = filled_tokens >= requested\n" +
                    "local new_tokens = filled_tokens\n" +
                    "local allowed_num = 0\n" +
                    "if allowed then\n" +
                    "    new_tokens = filled_tokens - requested\n" +
                    "    allowed_num = 1\n" +
                    "end\n" +
                    "redis.call('setex', tokens_key, ttl, new_tokens)\n" +
                    "redis.call('setex', timestamp_key, ttl, now)\n" +
                    "return { allowed_num, new_tokens }";


    /**
     * @param key      用于标识请求的key。
     * @param permits  请求需要的令牌数量。
     * @param rate     每秒生成的令牌数。
     * @param capacity 令牌桶容量。
     * @return
     */
    public boolean acquire(String key, double permits, double rate, int capacity) {
        long now = System.currentTimeMillis();

        String tokensKey = key + ":tokens";

        String timestampKey = key + ":timestamp";

        RScript script = redissonClient.getScript();
        List<Object> keyLists = Lists.newArrayList();
        keyLists.add(tokensKey);
        keyLists.add(timestampKey);
        Object[] result = script.eval(RScript.Mode.READ_WRITE, LUA_SCRIPT,
                RScript.ReturnType.MULTI, keyLists,
                String.valueOf(rate), String.valueOf(capacity), String.valueOf(now), String.valueOf(permits));

        int allowedNum = (Integer) result[0];

        int newTokens = (Integer) result[1];
        return allowedNum == 1;
    }
}
