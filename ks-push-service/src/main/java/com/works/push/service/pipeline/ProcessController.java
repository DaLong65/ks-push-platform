package com.works.push.service.pipeline;

import com.works.common.CommonConstants;
import com.works.push.service.model.PushTaskModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author DaLong
 * @date 2023/5/29 10:25
 * <p>
 * 流程控制器
 * @
 */
@Slf4j
@Data
public class ProcessController {

    private Map<String, ProcessChainTemplate> chainTemplateMap;


    public ProcessResult<?> process(ProcessContext<PushTaskModel> context) {
        log.info("[{}][processController] process, processBegin..", CommonConstants.SERVICE_NAME);
        List<ProcessActuator<PushTaskModel>> actuatorList = chainTemplateMap.get(context.getCode()).getProcessList();
        for (ProcessActuator<PushTaskModel> actuator : actuatorList) {
            actuator.process(context);
            if (context.getNeedBreak()) {
                break;
            }
        }
        return context.getResponse();
    }
}
