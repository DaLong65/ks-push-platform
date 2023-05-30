package com.works.push.service.handler.impl;

import com.works.enums.PushChannelEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.handler.BaseHandler;
import com.works.push.service.handler.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/5/29 16:50
 */
@Slf4j
@Component
public class SmsHandler extends BaseHandler implements Handler {

    public SmsHandler() {
        channelCode = PushChannelEnum.SMS.getType();
        //是否需要考虑限流，如果需要设置flowControlParam，不需要则不用设置
    }

    @Override
    public boolean handler(PushTaskInfo pushTaskInfo) {
        /**
         * 调用三方短信通道发消息
         */
        return false;
    }

    @Override
    public void recall() {

    }
}
