package com.works.push.service.deduplication.build;

import com.alibaba.fastjson.JSON;
import com.works.enums.MessageDeduplicationTypeEnum;
import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author DaLong
 * @date 2023/3/6 16:57
 * <p>
 * 相同内容去重
 */
@Component
@Slf4j
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {

    public ContentDeduplicationBuilder() {
        deduplicationType = MessageDeduplicationTypeEnum.CONTENT.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplicationConfig, PushTaskInfo pushTaskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplicationConfig, pushTaskInfo);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }

        deduplicationParam.setDeduplicationSingleKeyList(deduplicationSingleKey(pushTaskInfo));

        return deduplicationParam;
    }

    @Override
    public Map<String, String> deduplicationSingleKey(PushTaskInfo pushTaskInfo) {
        Set<String> receiverList = pushTaskInfo.getReceiver();
        Map<String, String> result = new HashMap<>(receiverList.size());
        for (String receiver : receiverList) {
            String param = pushTaskInfo.getMessageTemplateId() + receiver + JSON.toJSONString(pushTaskInfo.getMessageContentModel());
            result.put(receiver, param);
        }
        return result;
    }
}
