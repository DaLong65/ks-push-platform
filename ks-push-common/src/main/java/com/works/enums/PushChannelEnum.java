package com.works.enums;

import lombok.Getter;

/**
 * @author DaLong
 * @date 2023/3/1 15:15
 * <p>
 * 触达渠道枚举
 */
public enum PushChannelEnum {

    TELEPHONE(1, "telephone", "外呼"),

    SMS(2, "sms", "短信"),

    WECHAT(3, "weChat", "微信"),

    IM(4, "webSocket", "长链接"),

    STATION_NOTICE(5, "stationNotice", "站内通知"),

    STATION_MESSAGE(6, "stationMessage", "站内信"),
    NULL(-1, "NULL", "未知"),
    ;

    @Getter
    final int type;
    @Getter
    final String code;
    @Getter
    final String desc;

    PushChannelEnum(int type, String code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    public static PushChannelEnum queryByType(int type) {
        for (PushChannelEnum channelEnum : values()) {
            if (channelEnum.type == type) {
                return channelEnum;
            }
        }
        return NULL;
    }

}
