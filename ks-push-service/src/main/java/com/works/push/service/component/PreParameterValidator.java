package com.works.push.service.component;

import com.works.push.service.model.PushTaskModel;
import com.works.push.service.pipeline.ProcessActuator;
import com.works.push.service.pipeline.ProcessContext;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/5/29 10:51
 * <p>
 * 前置参数校验器
 */
@Component
public class PreParameterValidator implements ProcessActuator<PushTaskModel> {

    @Override
    public void process(ProcessContext<PushTaskModel> context) {
        /**
         * 参数验证
         */
    }
}
