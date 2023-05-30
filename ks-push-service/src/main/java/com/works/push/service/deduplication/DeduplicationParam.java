package com.works.push.service.deduplication;

import com.alibaba.fastjson.annotation.JSONField;
import com.works.info.PushTaskInfo;
import lombok.*;

import java.util.Map;

/**
 * @author DaLong
 * @date 2023/3/6 16:33
 *
 * 去重参数
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeduplicationParam {

    /**
     * push消息
     */
    private PushTaskInfo pushTaskInfo;

    /**
     * 去重时间
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需要达到去重的次数
     */
    @JSONField(name = "num")
    private Integer countNum;

    /**
     * 去重key
     */
    private Map<String,String> deduplicationSingleKeyList;

    /**
     * 拦截规则枚举
     */
}
