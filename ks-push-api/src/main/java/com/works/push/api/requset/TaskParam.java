package com.works.push.api.requset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * @author DaLong
 * @date 2023/5/29 14:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskParam {

    /**
     * 接受者
     */
    private Set<String> receivers;

    /**
     * 发送参数
     */
    private Map<String, String> param;
}
