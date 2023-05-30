package com.works.push.service.handler;

import com.alibaba.fastjson.JSON;
import com.works.common.CommonConstants;
import com.works.enums.MqTagEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.flowcontrol.FlowControlFactory;
import com.works.push.service.flowcontrol.FlowControlParam;
import com.works.push.service.mq.BizRocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author DaLong
 * @date 2023/3/6 14:52
 * <p>
 * 每个发送渠道的处理器
 */
@Slf4j
public abstract class BaseHandler implements Handler {

    @Resource
    private HandlerHolder handlerHolder;

    @Resource
    private FlowControlFactory flowControlFactory;

    /**
     * 标识渠道的Code
     * <p>
     * 子类初始化的时候指定
     */
    protected Integer channelCode;

    /**
     * 限流参数
     * <p>
     * 子类初始化指定
     */
    protected FlowControlParam flowControlParam;

    private static final SecureRandom secureRandom;


    @Value("${rocketmq.topic}")
    private String rocketMqTopic;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Resource
    private BizRocketMqProducer bizRocketMqProducer;

    /**
     * 初始化渠道和Handler的映射关系
     */
    @PostConstruct
    private void init() {
        handlerHolder.putHandler(channelCode, this);
    }

    static {
        try {
            secureRandom = SecureRandom.getInstance(CommonConstants.SECURE_RANDOM_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean flowControl(PushTaskInfo pushTaskInfo) {
        if (Objects.nonNull(flowControlParam)) {
            return flowControlFactory.flowControl(pushTaskInfo, flowControlParam);
        }
        return true;
    }

    @Override
    public void doHandler(PushTaskInfo pushTaskInfo) {
        if (!flowControl(pushTaskInfo)) {
            sendTimingMq(pushTaskInfo);
            return;
        }
        boolean result = handler(pushTaskInfo);
        if (result) {
            sendKafkaMq(pushTaskInfo);
        }
    }

    /**
     * 统一处理的handler接口
     *
     * @param pushTaskInfo
     * @return
     */
    public abstract boolean handler(PushTaskInfo pushTaskInfo);

    private void sendTimingMq(PushTaskInfo pushTaskInfo) {
        long seconds = secureRandom.nextInt(5);
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(seconds);
        bizRocketMqProducer.sendTiming(rocketMqTopic, JSON.toJSONString(pushTaskInfo), MqTagEnum.PUSH.getTag(), localDateTime);
    }


    private void sendKafkaMq(PushTaskInfo pushTaskInfo) {
        /**
         * 同送到Kafka队列，做后续数据统计
         */
    }

}
