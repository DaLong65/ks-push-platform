package com.works.push.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.works.common.CommonConstants;
import com.works.dto.DubboResponse;
import com.works.enums.ProcessRespStatusEnum;
import com.works.enums.ProcessTypeEnum;
import com.works.push.api.PushApi;
import com.works.push.api.requset.PushRequest;
import com.works.push.api.result.PushResult;
import com.works.push.service.model.PushTaskModel;
import com.works.push.service.pipeline.ProcessContext;
import com.works.push.service.pipeline.ProcessController;
import com.works.push.service.pipeline.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author DaLong
 * @date 2023/5/29 11:44
 */
@Slf4j
@DubboService
@Component
public class PushApiService implements PushApi {

    @Resource
    private ProcessController processController;

    @Override
    public DubboResponse<PushResult> push(PushRequest request) {
        log.info("[{}][PushApiService][push] request:{}", CommonConstants.SERVICE_NAME, JSON.toJSONString(request));
        PushTaskModel pushTaskModel = PushTaskModel.builder()
                .pushTaskId(request.getPushTaskId())
                .taskParamList(Collections.singletonList(request.getTaskParam())).build();

        ProcessContext<PushTaskModel> context = ProcessContext.<PushTaskModel>builder()
                .code(ProcessTypeEnum.TASK_PUSH.getType())
                .processData(pushTaskModel)
                .needBreak(false)
                .response(ProcessResult.success()).build();

        ProcessResult<?> result = processController.process(context);
        if (result.getStatus() == ProcessRespStatusEnum.SUCCESS.getCode()) {
            return DubboResponse.success();
        }
        return DubboResponse.failure(result.getMessage());
    }
}
