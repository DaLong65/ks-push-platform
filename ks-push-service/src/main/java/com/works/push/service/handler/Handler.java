package com.works.push.service.handler;

import com.works.info.PushTaskInfo;

/**
 * @author DaLong
 * @date 2023/3/6 11:43
 *
 * 消息处理器
 */
public interface Handler {

    /**
     * 处理器
     * @param pushTaskInfo
     */
    void doHandler(PushTaskInfo pushTaskInfo);

    /**
     * 撤回消息
     */
    void recall();
}
