package com.works.enums;

import lombok.Getter;

/**
 * @author DaLong
 * @date 2023/5/11 11:33
 */
public enum MqTagEnum {

    PUSH("msg_push","消息推送"),
    ;

    @Getter
    final String tag;
    @Getter
    final String desc;

    MqTagEnum(String tag, String desc) {
        this.tag = tag;
        this.desc = desc;
    }
}
