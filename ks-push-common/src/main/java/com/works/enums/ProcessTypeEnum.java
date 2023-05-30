package com.works.enums;

import lombok.Getter;

/**
 * @author DaLong
 * @date 2023/5/29 11:02
 */
public enum ProcessTypeEnum {

    TASK_PUSH("task_push", "任务推送"),

    TASK_RECALL("task_recall", "任务召回"),
    ;

    @Getter
    final String type;

    @Getter
    final String desc;

    ProcessTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
