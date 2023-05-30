package com.works.enums;

import lombok.Getter;

/**
 * @author DaLong
 * @date 2023/3/6 11:24
 */
public enum MessageTypeEnum {

    /**
     * 通知类消息
     */
    NOTICE(1, "notice", "通知类消息"),

    MARKETING(2, "marketing", "营销类消息"),

    AUTH_CODE(3, "authCode", "验证码类消息"),

    NULL(4, "null", "不存在");

    @Getter
    final int type;
    @Getter
    final String code;
    @Getter
    final String desc;

    MessageTypeEnum(int type, String code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    public static MessageTypeEnum queryByType(int type) {
        for (MessageTypeEnum typeEnum : values()) {
            if (typeEnum.type == type) {
                return typeEnum;
            }
        }
        return NULL;
    }
}
