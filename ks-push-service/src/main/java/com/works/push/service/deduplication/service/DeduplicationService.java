package com.works.push.service.deduplication.service;

import com.works.push.service.deduplication.DeduplicationParam;

/**
 * @author DaLong
 * @date 2023/3/6 17:02
 * <p>
 * 去重服务
 */
public interface DeduplicationService {

    /**
     * 去重
     *
     * @param param
     */
    void deduplication(DeduplicationParam param);
}
