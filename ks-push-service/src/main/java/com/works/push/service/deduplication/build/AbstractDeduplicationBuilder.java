package com.works.push.service.deduplication.build;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.works.info.PushTaskInfo;
import com.works.push.service.deduplication.DeduplicationHolder;
import com.works.push.service.deduplication.DeduplicationParam;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author DaLong
 * @date 2023/3/6 16:58
 * <p>
 * 去重参数构建
 */
public abstract class AbstractDeduplicationBuilder implements Builder {

    /**
     * 去重类型
     */
    protected Integer deduplicationType;
    @Resource
    private DeduplicationHolder deduplicationHolder;

    private static final String DEDUPLICATION_CONFIG_PRE = "deduplication_";

    @PostConstruct
    private void init() {
        deduplicationHolder.putBuilder(deduplicationType, this);
    }

    public DeduplicationParam getParamsFromConfig(Integer key, String duplicationConfig, PushTaskInfo pushTaskInfo) {
        if (StringUtils.isNotBlank(duplicationConfig)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(duplicationConfig);
        if (Objects.isNull(jsonObject)) {
            return null;
        }

        String paramKey = DEDUPLICATION_CONFIG_PRE + key;

        DeduplicationParam deduplicationParam = JSON.parseObject(jsonObject.getString(paramKey), DeduplicationParam.class);

        deduplicationParam.setPushTaskInfo(pushTaskInfo);

        return deduplicationParam;
    }


    /**
     * 去重key映射表
     *
     * @param pushTaskInfo
     * @return
     */
    public abstract Map<String, String> deduplicationSingleKey(PushTaskInfo pushTaskInfo);

    //todo
    public static void main(String[] args) {
        String config = "{\"deduplication_10\":{\"num\":1,\"time\":300},\"deduplication_20\":{\"num\":5}}";

        JSONObject object = JSONObject.parseObject(config);

        DeduplicationParam param = JSON.parseObject(object.getString("deduplication_10"), DeduplicationParam.class);
        System.out.println();
    }
}
