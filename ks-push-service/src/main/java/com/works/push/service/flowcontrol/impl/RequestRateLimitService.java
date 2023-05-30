package com.works.push.service.flowcontrol.impl;

import com.works.enums.RateLimitStrategyEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.flowcontrol.DistributedRateLimiter;
import com.works.push.service.flowcontrol.FlowControlParam;
import com.works.push.service.flowcontrol.FlowControlService;
import com.works.push.service.flowcontrol.annottations.RateLimitStrategy;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author DaLong
 * @date 2023/3/9 15:34
 * <p>
 * 根据真实请求数限流
 */
@Slf4j
@RateLimitStrategy(rateLimitStrategy = RateLimitStrategyEnum.REQUEST_RATE_LIMIT)
public class RequestRateLimitService implements FlowControlService {

    @Resource
    private DistributedRateLimiter distributedRateLimiter;

    @Override
    public Boolean flowControl(PushTaskInfo pushTaskInfo, FlowControlParam flowControlParam) {
        String key = DISTRIBUTED_RATE_LIMIT_KEY_ + pushTaskInfo.getPushChannelType();
        Boolean result = distributedRateLimiter.acquire(key, 1.0, flowControlParam.getRateInitValue(), flowControlParam.getRateInitValue().intValue());
        log.info("[ks-intelligent-push-service][RequestRateLimitService] key:{},result:{}", key, result);
        return result;
    }
}
