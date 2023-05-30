package com.works.push.service.component;

import com.works.common.RedisConstants;
import com.works.enums.ProcessRespStatusEnum;
import com.works.push.service.model.PushTaskModel;
import com.works.push.service.pipeline.ProcessActuator;
import com.works.push.service.pipeline.ProcessContext;
import com.works.push.service.pipeline.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author DaLong
 * @date 2023/5/29 10:53
 * <p>
 * 参数组装器
 */
@Slf4j
@Component
public class ParameterAssembler implements ProcessActuator<PushTaskModel> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void process(ProcessContext<PushTaskModel> context) {
        PushTaskModel pushTaskModel = context.getProcessData();
        Long pushTaskId = pushTaskModel.getPushTaskId();
        String taskInfoValue = stringRedisTemplate.opsForValue().get(RedisConstants.getPushTaskRedisKey(pushTaskId));
        if (StringUtils.isBlank(taskInfoValue)) {
            context.setNeedBreak(true).setResponse(ProcessResult.fail(ProcessRespStatusEnum.FAIL));
            return;
        }
        //组装业务参数
    }
}
