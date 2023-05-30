package com.works.push.service.flowcontrol.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.works.enums.RateLimitStrategyEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.flowcontrol.FlowControlParam;
import com.works.push.service.flowcontrol.FlowControlService;
import com.works.push.service.flowcontrol.annottations.RateLimitStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DaLong
 * @date 2023/3/9 15:59
 * <p>
 * 单机限流
 */
@Slf4j
@RateLimitStrategy(rateLimitStrategy = RateLimitStrategyEnum.LOCAL_RATE_LIMIT)
public class LocalRateLimitService implements FlowControlService {

    private final static Map<Integer, RateLimiter> localChannelRateLimitMap = new ConcurrentHashMap<>();

    @Override
    public Boolean flowControl(PushTaskInfo pushTaskInfo, FlowControlParam flowControlParam) {
        log.info("[ks-intelligent-push-service][LocalRateLimitService] ...");
        RateLimiter rateLimiter = getRateLimiter(pushTaskInfo.getPushChannelType(),flowControlParam.getRateInitValue());
        return rateLimiter.tryAcquire();
    }

    public static RateLimiter getRateLimiter(int channelId, double permitsPerSecond) {
        return localChannelRateLimitMap.computeIfAbsent(channelId, k -> RateLimiter.create(permitsPerSecond));
    }
}
