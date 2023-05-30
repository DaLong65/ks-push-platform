package com.works.push.service.pipeline;

import com.works.push.service.model.PushTaskModel;
import lombok.Data;

import java.util.List;

/**
 * @author DaLong
 * @date 2023/5/29 11:00
 * <p>
 * 任务执行链模版
 */
@Data
public class ProcessChainTemplate {

    private List<ProcessActuator<PushTaskModel>> processList;
}
