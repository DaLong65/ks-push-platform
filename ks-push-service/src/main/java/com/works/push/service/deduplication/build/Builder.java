package com.works.push.service.deduplication.build;

import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationParam;

/**
 * @author DaLong
 * @date 2023/5/29 15:56
 */
public interface Builder {

    /**
     * 根据配置构建去重参数
     */
    DeduplicationParam build(String deduplicationConfig, PushTaskInfo pushTaskInfo);

}
