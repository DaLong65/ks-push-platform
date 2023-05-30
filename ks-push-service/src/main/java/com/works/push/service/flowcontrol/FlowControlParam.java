package com.works.push.service.flowcontrol;

import com.works.enums.RateLimitStrategyEnum;
import lombok.*;

/**
 * @author DaLong
 * @date 2023/3/6 14:55
 * <p>
 * 流量控制参数
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlowControlParam {

    /**
     * 限流器初始限流大小
     */
    private Double rateInitValue;

    /**
     * 限流的策略
     */
    private RateLimitStrategyEnum rateLimitStrategyEnum;
}
