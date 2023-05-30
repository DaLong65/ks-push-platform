package com.works.push.service.model;

import com.works.info.PushTaskInfo;
import com.works.push.api.requset.TaskParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author DaLong
 * @date 2023/5/27 19:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushTaskModel {

    /**
     * 推送任务ID
     */
    private Long pushTaskId;

    /**
     * 推送任务信息
     */
    private PushTaskInfo pushTaskInfo;

    /**
     * 请求参数
     */
    private List<TaskParam> taskParamList;
}
