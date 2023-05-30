package com.works.push.service.pipeline;

/**
 * @author DaLong
 * @date 2023/5/29 10:32
 * <p>
 * 流程执行器
 */
public interface ProcessActuator<T> {

    /**
     * 逻辑处理
     */
    void process(ProcessContext<T> context);

}
