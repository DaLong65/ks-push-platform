package com.works.push.api.requset;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DaLong
 * @date 2023/5/29 11:46
 */
@Data
public class PushRequest implements Serializable {

    /**
     * 推送任务ID
     */
    private Long pushTaskId;

    /**
     * 推送核心参数
     */
    private TaskParam taskParam;
}
