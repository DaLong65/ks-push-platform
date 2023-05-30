package com.works.push.service.flowcontrol;


import com.works.info.PushTaskInfo;

/**
 * @author DaLong
 * @date 2023/3/9 15:29
 * <p>
 * 流量控制
 */
public interface FlowControlService {

    String DISTRIBUTED_RATE_LIMIT_KEY_ = "distributed_rate_limit_key:";

    /**
     * 根据渠道进行流量控制
     *
     * @param pushTaskInfo
     * @param flowControlParam
     * @return
     */
    Boolean flowControl(PushTaskInfo pushTaskInfo, FlowControlParam flowControlParam);
}
