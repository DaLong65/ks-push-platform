package com.works.push.service.handler.impl;

import com.works.enums.PushChannelEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.handler.BaseHandler;
import com.works.push.service.handler.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/5/29 16:52
 */
@Slf4j
@Component
public class MobileHandler extends BaseHandler implements Handler {

    public MobileHandler(){
        channelCode = PushChannelEnum.TELEPHONE.getType();
        //是否需要考虑限流，如果需要设置flowControlParam，不需要则不用设置
    }

    @Override
    public boolean handler(PushTaskInfo pushTaskInfo) {
        /**
         * 调用三方外呼通道
         */
        return false;
    }

    @Override
    public void recall() {

    }
}
