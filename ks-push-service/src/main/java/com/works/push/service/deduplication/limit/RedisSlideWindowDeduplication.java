package com.works.push.service.deduplication.limit;

import cn.hutool.core.lang.Snowflake;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * @author DaLong
 * @date 2023/3/7 17:51
 * <p>
 * 活动窗口去重
 */
@Slf4j
@Component
public class RedisSlideWindowDeduplication {

    private static final String LOCK_KEY_PREFIX = "lock:";
    private static final String WINDOW_KEY_PREFIX = "window:";


    /**
     * local window_key = KEYS[1]：定义一个名为 window_key 的变量，它的值为传入 KEYS 列表中的第一个元素，即 Redis 中用于实现滑动窗口的有序集合的键名。
     *
     * local id = ARGV[1]：定义一个名为 id 的变量，它的值为传入 ARGV 列表中的第一个元素，即要被限流的请求的唯一标识符。
     *
     * local window_size = tonumber(ARGV[2])：定义一个名为 window_size 的变量，它的值为传入 ARGV 列表中的第二个元素，即滑动窗口的大小。
     *
     * local window_time = tonumber(ARGV[3])：定义一个名为 window_time 的变量，它的值为传入 ARGV 列表中的第三个元素，即滑动窗口的时间长度（单位为秒）。
     *
     * local current_time = tonumber(redis.call('TIME')[1])：定义一个名为 current_time 的变量，它的值为当前时间戳，使用 Redis 的 TIME 命令获取。这个时间戳是一个数组，我们只需要其中的第一个元素，即秒数部分，因此使用 [1] 取出。
     *
     * redis.call('ZREMRANGEBYSCORE', window_key, '-inf', current_time - window_time)：使用 Redis 的 ZREMRANGEBYSCORE 命令，删除有序集合中时间戳小于当前时间戳减去窗口时间长度的元素，即将滑动窗口向右移动，删除旧的元素。
     *
     * local window_count = redis.call('ZCOUNT', window_key, '-inf', '+inf')：使用 Redis 的 ZCOUNT 命令，获取有序集合中元素数量，即当前滑动窗口中的请求数量。
     *
     * if window_count >= window_size then\n return 0\nend：如果滑动窗口中的请求数量大于等于滑动窗口的大小，说明滑动窗口已满，返回 0，即拒绝当前请求。
     *
     * redis.call('ZADD', window_key, current_time, id)：如果滑动窗口未满，将当前请求的唯一标识符作为元素加入有序集合，时间戳为当前时间戳。
     *
     * return 1：返回 1，表示允许当前请求通过限流。
     */
    private static final String LUA_SCRIPT =
            "local window_key = KEYS[1]\n" +
                    "local id = ARGV[1]\n" +
                    "local window_size = tonumber(ARGV[2])\n" +
                    "local window_time = tonumber(ARGV[3])\n" +
                    "local current_time = tonumber(redis.call('TIME')[1])\n" +
                    "redis.call('ZREMRANGEBYSCORE', window_key, '-inf', current_time - window_time)\n" +
                    "local window_count = redis.call('ZCOUNT', window_key, '-inf', '+inf')\n" +
                    "if window_count >= window_size then\n" +
                    "    return 0\n" +
                    "end\n" +
                    "redis.call('ZADD', window_key, current_time, id)\n" +
                    "return 1";

    @Resource
    private RedissonClient redissonClient;

    @Value("${ks.intelligent.push.slide.window.size}")
    private int windowSize;
    @Value("${ks.intelligent.push.slide.window.time}")
    private int windowTime;

    private final Snowflake snowflake;

    public RedisSlideWindowDeduplication() {
        snowflake = new Snowflake();
    }


    public boolean deduplicate(String key) {

        String lockKey = LOCK_KEY_PREFIX + key;

        String windowKey = WINDOW_KEY_PREFIX + key;

        String id = String.valueOf(snowflake.nextId());

        long currentTime = System.currentTimeMillis();

        // 获取分布式锁
        redissonClient.getLock(lockKey).lock();

        try {
            // 判断ID是否在滑动窗口中
            RScript script = redissonClient.getScript(StringCodec.INSTANCE);
            List<Object> paramList = Lists.newArrayList();
            paramList.add(windowKey);
            Object result = script.eval(RScript.Mode.READ_WRITE, LUA_SCRIPT,
                    RScript.ReturnType.INTEGER,
                    paramList,
                    id, String.valueOf(windowSize), String.valueOf(windowTime));
            if (Objects.isNull(result)) {
                return false;
            }
            int success = (Integer) result;
            if (success == 0) {
                return false;
            }

            // 将ID存储在Redis中的滑动窗口中
            RBucket<Long> bucket = redissonClient.getBucket(id);
            bucket.set(currentTime);
            bucket.expire(Instant.ofEpochSecond(windowTime));
            return true;

        } finally {
            redissonClient.getLock(lockKey).unlock();
        }
    }
}
