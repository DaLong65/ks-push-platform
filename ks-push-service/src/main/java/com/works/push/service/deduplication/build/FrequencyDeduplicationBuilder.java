package com.works.push.service.deduplication.build;

import cn.hutool.core.date.DateUtil;
import com.works.enums.MessageDeduplicationTypeEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author DaLong
 * @date 2023/3/6 17:16
 * <p>
 * 相同的渠道去重
 */
@Slf4j
@Component
public class FrequencyDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {


    private static final String PREFIX = "FRE";


    public FrequencyDeduplicationBuilder() {
        deduplicationType = MessageDeduplicationTypeEnum.FREQUENCY.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplicationConfig, PushTaskInfo pushTaskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplicationConfig, pushTaskInfo);
        if(Objects.isNull(deduplicationParam)){
            return null;
        }
        deduplicationParam.setDeduplicationSingleKeyList(deduplicationSingleKey(pushTaskInfo));

        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);

        return deduplicationParam;
    }

    /**
     * 业务规则去重 构建key
     * <p>
     * key ： receiver + templateId + sendChannel
     * <p>
     * 一天内一个用户只能收到某个渠道的消息 N 次
     *
     * @return
     */
    @Override
    public Map<String, String> deduplicationSingleKey(PushTaskInfo pushTaskInfo) {
        Set<String> receiverList = pushTaskInfo.getReceiver();

        Map<String, String> result = new HashMap<>(receiverList.size());

        for (String receiver : receiverList) {
            String param = PREFIX + "_" + receiver + "_" + pushTaskInfo.getMessageTemplateId() + "_" + pushTaskInfo.getPushChannelType();
            result.put(receiver, param);
        }
        return result;
    }
}
