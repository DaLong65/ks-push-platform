package com.works.enums;

import lombok.Getter;

/**
 * @author DaLong
 * @date 2023/5/29 11:54
 */
public enum ResponseCode {

    SUCCESS(0, "请求成功"),
    FAILURE(-1, "请求失败");

    @Getter
    final int code;
    @Getter
    final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
