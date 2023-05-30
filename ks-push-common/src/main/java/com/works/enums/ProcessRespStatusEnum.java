package com.works.enums;

import lombok.Getter;

/**
 * @author DaLong
 * @date 2023/3/10 14:47
 */
public enum ProcessRespStatusEnum {

    SUCCESS(1, "操作成功"),
    FAIL(0, "操作失败"),
    ;

    @Getter
    final int code;
    @Getter
    final String desc;

    ProcessRespStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
