package com.works.push.service.deduplication.service;

import com.works.enums.MessageDeduplicationTypeEnum;
import com.works.push.service.deduplication.limit.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/3/7 17:13
 * <p>
 * 频次去重
 */
@Slf4j
@Component
public class FrequencyDeduplicationService extends AbstractDeduplicationService {

    @Autowired
    public FrequencyDeduplicationService(@Qualifier(value = "simpleLimitService") LimitService limitService) {
        this.limitService = limitService;
        deduplicationType = MessageDeduplicationTypeEnum.FREQUENCY.getCode();
    }

}
