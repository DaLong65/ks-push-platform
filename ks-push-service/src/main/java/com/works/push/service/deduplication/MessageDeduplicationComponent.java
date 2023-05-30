package com.works.push.service.deduplication;

import com.works.enums.MessageDeduplicationTypeEnum;
import com.works.info.PushTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author DaLong
 * @date 2023/5/29 15:53
 */
@Slf4j
@Component
public class MessageDeduplicationComponent {

    @Resource
    private DeduplicationHolder deduplicationHolder;
    @Value("${push.deduplication.config}")
    private String deduplicationConfig;

    public void duplication(PushTaskInfo pushTaskInfo){
        if (Objects.isNull(pushTaskInfo.getDeduplicationType()) || MessageDeduplicationTypeEnum.NO.getCode() == pushTaskInfo.getDeduplicationType()) {
            return;
        }

        if (MessageDeduplicationTypeEnum.BOTH_ALL.getCode() == pushTaskInfo.getDeduplicationType()) {
            // 去重，这里可以通过后台来传入
            List<Integer> deduplicationList = MessageDeduplicationTypeEnum.getDeduplicationList();

            for (Integer deduplicationType : deduplicationList) {

                DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, pushTaskInfo);

                if (!Objects.isNull(deduplicationParam)) {
                    deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
                }
            }
        } else {
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(pushTaskInfo.getDeduplicationType()).build(deduplicationConfig, pushTaskInfo);

            if (!Objects.isNull(deduplicationParam)) {
                deduplicationHolder.selectService(pushTaskInfo.getDeduplicationType()).deduplication(deduplicationParam);
            }
        }
    }
}
