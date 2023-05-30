package com.works.push.service.deduplication;

import com.works.push.service.deduplication.build.Builder;
import com.works.push.service.deduplication.service.DeduplicationService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DaLong
 * @date 2023/3/6 16:27
 * <p>
 * 去重类型关系映射
 */
@Component
public class DeduplicationHolder {

    private final Map<Integer, Builder> builderHolder = new HashMap<>(4);

    private final Map<Integer, DeduplicationService> serviceHolder = new HashMap<>(4);

    public Builder selectBuilder(Integer key) {
        return builderHolder.get(key);
    }

    public DeduplicationService selectService(Integer key) {
        return serviceHolder.get(key);
    }

    public void putBuilder(Integer key, Builder builder) {
        builderHolder.put(key, builder);
    }

    public void putService(Integer key, DeduplicationService service) {
        serviceHolder.put(key, service);
    }


}
