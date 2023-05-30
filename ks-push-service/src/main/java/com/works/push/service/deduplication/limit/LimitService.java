package com.works.push.service.deduplication.limit;

import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationParam;

import java.util.Set;

/**
 * @author DaLong
 * @date 2023/5/29 16:04
 *
 * 去重限制规则
 */
public interface LimitService {

    /**
     * 去重限制
     * @param pushTaskInfo
     * @param param
     * @return
     */
    Set<String> limitFilter(PushTaskInfo pushTaskInfo, DeduplicationParam param);
}
