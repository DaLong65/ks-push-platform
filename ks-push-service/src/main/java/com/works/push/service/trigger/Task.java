package com.works.push.service.trigger;

import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.MessageDeduplicationComponent;
import com.works.push.service.discard.MessageDiscardComponent;
import com.works.push.service.handler.HandlerHolder;
import com.works.push.service.shield.MessageShieldComponent;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author DaLong
 * @date 2023/5/29 15:42
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable {

    private PushTaskInfo pushTaskInfo;

    @Resource
    private HandlerHolder handlerHolder;
    @Resource
    private MessageDiscardComponent messageDiscardComponent;
    @Resource
    private MessageShieldComponent messageShieldComponent;
    @Resource
    private MessageDeduplicationComponent messageDeduplicationComponent;

    @Override
    public void run() {
        //消息丢弃
        if(messageDiscardComponent.isDiscard(pushTaskInfo)){
            return;
        }
        //2、屏蔽消息
        messageShieldComponent.shield(pushTaskInfo);
        //3.去重
        messageDeduplicationComponent.duplication(pushTaskInfo);
        //4、发送消息
        handlerHolder.route(pushTaskInfo.getPushChannelType()).doHandler(pushTaskInfo);
    }
}
