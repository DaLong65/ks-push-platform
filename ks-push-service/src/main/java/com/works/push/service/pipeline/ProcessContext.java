package com.works.push.service.pipeline;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author DaLong
 * @date 2023/5/29 10:33
 * <p>
 * 责任链上下文
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@AllArgsConstructor
public class ProcessContext<T> {

    /**
     * 责任链标识：Code
     */
    private String code;

    /**
     * 上下文数据
     */
    private T processData;

    /**
     * 是否需要中断
     */
    private Boolean needBreak;

    /**
     * 结果
     */
    private ProcessResult<?> response;
}
