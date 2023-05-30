package com.works.common;

/**
 * @author DaLong
 * @date 2023/5/29 15:14
 */
public class RedisConstants {

    private static final String PUSH_TASK_REDIS_KEY = "push_task_key:";

    public static String getPushTaskRedisKey(Long pushTaskId) {
        return PUSH_TASK_REDIS_KEY + pushTaskId;
    }
}
