package com.works.push.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author DaLong
 * @date 2023/5/29 15:18
 */
@Slf4j
@Component
public class BizRocketMqProducer {

    public void send(String topic,String content,String tag){
        /**
         * RocketMQ 发送
         */
    }

    public void sendTiming(String topic, String content, String tag, LocalDateTime localDateTime) {
        /**
         * RocketMQ 延迟消息推送
         */
    }
}
