package com.works.push.service.mq.handler;

import com.alibaba.fastjson.JSON;
import com.works.info.PushTaskInfo;
import com.works.push.service.trigger.Task;
import com.works.push.service.trigger.TaskTrigger;
import com.works.utils.GroupIdMappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author DaLong
 * @date 2023/5/29 15:27
 */
@Slf4j
@Component
public class PushTaskMqHandler {

    @Resource
    private ApplicationContext context;

    @Resource
    private TaskTrigger taskTrigger;


    public void execute(String content){
        if(StringUtils.isBlank(content)){
            return;
        }
        PushTaskInfo pushTaskInfo = JSON.parseObject(content, PushTaskInfo.class);
        String threadGroupId = GroupIdMappingUtils.getGroupIdByPushTaskInfo(pushTaskInfo);

        Task task = context.getBean(Task.class).setPushTaskInfo(pushTaskInfo);

        taskTrigger.route(threadGroupId).execute(task);
    }

}
