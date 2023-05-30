package com.works.push.service.deduplication.service;

import com.works.enums.MessageDeduplicationTypeEnum;
import com.works.push.service.deduplication.limit.SlideWindowLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/3/7 16:50
 * <p>
 * 内容去重服务
 */

@Slf4j
@Component
public class ContentDeduplicationService extends AbstractDeduplicationService {


    public ContentDeduplicationService() {
        deduplicationType = MessageDeduplicationTypeEnum.CONTENT.getCode();
        limitService = new SlideWindowLimitService();
    }
}
