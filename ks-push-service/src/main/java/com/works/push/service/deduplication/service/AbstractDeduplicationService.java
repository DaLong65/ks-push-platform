package com.works.push.service.deduplication.service;

import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationHolder;
import com.works.push.service.deduplication.DeduplicationParam;
import com.works.push.service.deduplication.limit.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

/**
 * @author DaLong
 * @date 2023/5/29 16:00
 */
@Slf4j
public abstract class AbstractDeduplicationService implements DeduplicationService {

    @Resource
    private DeduplicationHolder deduplicationHolder;

    protected Integer deduplicationType;

    protected LimitService limitService;


    @PostConstruct
    private void init() {
        deduplicationHolder.putService(deduplicationType, this);
    }

    @Override
    public void deduplication(DeduplicationParam param) {
        PushTaskInfo pushTaskInfo = param.getPushTaskInfo();
        //拿到需要过滤的数据
        Set<String> filterReceiver = limitService.limitFilter(pushTaskInfo, param);
        if (!CollectionUtils.isEmpty(filterReceiver)) {
            pushTaskInfo.getReceiver().removeAll(filterReceiver);
        }
    }
}
