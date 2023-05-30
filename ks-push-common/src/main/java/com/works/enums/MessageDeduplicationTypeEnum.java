package com.works.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * @author DaLong
 * @date 2023/3/6 16:23
 *
 */
public enum MessageDeduplicationTypeEnum {

    CONTENT(10, "N分钟相同内容去重"),

    FREQUENCY(20, "一天内N次相同的渠道去重"),

    BOTH_ALL(30,"都要"),

    NO(0,"不去重"),

    ;

    @Getter
    final int code;
    @Getter
    final String desc;

    MessageDeduplicationTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取去重渠道的列表
     *
     * @return
     */
    public static List<Integer> getDeduplicationList() {
        List<Integer> list = Lists.newArrayList();
        for (MessageDeduplicationTypeEnum typeEnum : values()) {
            list.add(typeEnum.getCode());
        }
        return list;
    }
}
