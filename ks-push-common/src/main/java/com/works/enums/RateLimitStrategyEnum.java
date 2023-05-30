package com.works.enums;

import lombok.Getter;

/**
 * @author DaLong
 * @date 2023/3/8 18:18
 */
public enum RateLimitStrategyEnum {

    REQUEST_RATE_LIMIT(100, "根据真实请求数限流"),

    SEND_USER_NUM_RATE_LIMIT(200, "根据发送用户数限流"),

    LOCAL_RATE_LIMIT(300,"单机限流"),

    ;

    @Getter
    final int code;
    @Getter
    final String desc;

    RateLimitStrategyEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
