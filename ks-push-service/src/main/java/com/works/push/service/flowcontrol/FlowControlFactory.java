package com.works.push.service.flowcontrol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.works.enums.RateLimitStrategyEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.flowcontrol.annottations.RateLimitStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DaLong
 * @date 2023/3/6 14:54
 * <p>
 * 流控⚠工厂
 */
@Slf4j
@Component
public class FlowControlFactory implements ApplicationContextAware {

    /**
     * {"flow_control_1":1}
     */
    @Value("${ks.channel.flow.control.strategy}")
    private String ksChannelFlowControlStrategy;

    private static final String FLOW_CONTROL_PREFIX = "flow_control_";

    private final Map<RateLimitStrategyEnum, FlowControlService> flowControlServiceMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, FlowControlService> beanMap = applicationContext.getBeansOfType(FlowControlService.class);
        if (!CollectionUtils.isEmpty(beanMap)) {
            for (Map.Entry<String, FlowControlService> entry : beanMap.entrySet()) {
                FlowControlService flowControlService = entry.getValue();
                if (flowControlService.getClass().isAnnotationPresent(RateLimitStrategy.class)) {
                    RateLimitStrategy strategy = flowControlService.getClass().getAnnotation(RateLimitStrategy.class);
                    RateLimitStrategyEnum strategyEnum = strategy.rateLimitStrategy();
                    flowControlServiceMap.put(strategyEnum, flowControlService);
                }
            }
        }
    }


    /**
     * 流量控制，
     *
     * @param pushTaskInfo
     * @param flowControlParam
     * @return true:表示当前能够获得到许可；false:表示当前无法获得到需求。
     */
    public Boolean flowControl(PushTaskInfo pushTaskInfo, FlowControlParam flowControlParam) {
        Double rateInitValue = getRateLimitConfig(pushTaskInfo.getPushChannelType());
        if (0 == rateInitValue.intValue()) {
            return true;
        }
        flowControlParam.setRateInitValue(rateInitValue);
        FlowControlService flowControlService = flowControlServiceMap.get(flowControlParam.getRateLimitStrategyEnum());
        return flowControlService.flowControl(pushTaskInfo, flowControlParam);
    }


    private Double getRateLimitConfig(int pushChannelType) {
        if (StringUtils.isBlank(ksChannelFlowControlStrategy)) {
            return 0d;
        }
        JSONObject jsonObject = JSON.parseObject(ksChannelFlowControlStrategy);
        if (Objects.isNull(jsonObject)) {
            return 0d;
        }
        if (Objects.isNull(jsonObject.getDouble(FLOW_CONTROL_PREFIX + pushChannelType))) {
            return 0d;
        }

        return jsonObject.getDouble(FLOW_CONTROL_PREFIX + pushChannelType);
    }

}
