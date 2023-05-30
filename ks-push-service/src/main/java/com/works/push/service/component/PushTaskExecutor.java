package com.works.push.service.component;

import com.alibaba.fastjson.JSON;
import com.works.enums.MqTagEnum;
import com.works.enums.ProcessTypeEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.model.PushTaskModel;
import com.works.push.service.mq.BizRocketMqProducer;
import com.works.push.service.pipeline.ProcessActuator;
import com.works.push.service.pipeline.ProcessContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author DaLong
 * @date 2023/5/29 10:55
 * <p>
 * 推送任务执行器
 */
@Slf4j
@Component
public class PushTaskExecutor implements ProcessActuator<PushTaskModel> {

    @Resource
    private BizRocketMqProducer bizRocketMqProducer;
    @Value("${push.topic}")
    private String pushTopic;

    @Override
    public void process(ProcessContext<PushTaskModel> context) {
        PushTaskModel pushTaskModel = context.getProcessData();

        if (ProcessTypeEnum.TASK_PUSH.getType().equals(context.getCode())) {
            PushTaskInfo pushTaskInfo = pushTaskModel.getPushTaskInfo();

            bizRocketMqProducer.send(pushTopic, JSON.toJSONString(pushTaskInfo), MqTagEnum.PUSH.getTag());
        }
    }
}
