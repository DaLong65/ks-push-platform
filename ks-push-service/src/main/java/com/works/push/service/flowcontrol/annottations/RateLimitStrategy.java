package com.works.push.service.flowcontrol.annottations;

import com.works.enums.RateLimitStrategyEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author DaLong
 * @date 2023/3/9 15:57
 * <p>
 * 限流策略
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RateLimitStrategy {

    RateLimitStrategyEnum rateLimitStrategy() default RateLimitStrategyEnum.REQUEST_RATE_LIMIT;
}
