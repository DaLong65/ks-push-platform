package com.works.push.service.deduplication.limit;

import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationParam;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author DaLong
 * @date 2023/3/6 18:17
 * <p>
 * 限制每天发送的条数
 */
@Component(value = "simpleLimitService")
public class SimpleLimitService extends AbstractLimitService {


    @Override
    public Set<String> limitFilter(PushTaskInfo pushTaskInfo, DeduplicationParam param) {
        Set<String> filterReceiver = new HashSet<>(pushTaskInfo.getReceiver().size());
        /**
         * 根据具体业务规则去重。
         */

        return filterReceiver;
    }
}
