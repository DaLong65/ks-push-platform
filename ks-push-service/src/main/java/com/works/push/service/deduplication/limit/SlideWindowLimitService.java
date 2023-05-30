package com.works.push.service.deduplication.limit;

import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author DaLong
 * @date 2023/3/6 18:36
 * <p>
 * 滑动窗口去重器
 * （内容去重采用基于redis中zset的滑动窗口去重，可以做到严格控制单位时间内的频次。）
 */
@Slf4j
@Component(value = "slideWindowLimitService")
public class SlideWindowLimitService extends AbstractLimitService {

    @Resource
    private RedisSlideWindowDeduplication redisSlideWindowDeduplication;

    private static final String LIMIT_TAG = "SW_";

    @Override
    public Set<String> limitFilter(PushTaskInfo pushTaskInfo, DeduplicationParam param) {
        Set<String> filterReceiver = new HashSet<>(pushTaskInfo.getReceiver().size());

        Map<String, String> userKeysMap = param.getDeduplicationSingleKeyList();

        for (String receiver : pushTaskInfo.getReceiver()) {
            String key = LIMIT_TAG + userKeysMap.get(receiver);

            if (redisSlideWindowDeduplication.deduplicate(key)) {
                filterReceiver.add(receiver);
            }
        }
        return filterReceiver;
    }
}
