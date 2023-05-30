package com.works.info;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

/**
 * @author DaLong
 * @date 2023/5/27 20:40
 */
@Data
@Builder
@ToString(callSuper = true)
public class PushTaskInfo {

    /**
     * 推送渠道类型
     * 外呼，短信，微信，IM，站内信、站内通知
     */
    private Integer pushChannelType;

    /**
     * 推送消息类型
     * 通知类，营销类，验证码类
     */
    private Integer pushMessageType;

    /**
     * 接收者
     */
    private Set<String> receiver;

    /**
     * 发送的Id类型(手机号/？/？)
     */
    private Integer idType;

    /**
     * 屏蔽类型
     * 夜间不屏蔽；夜间屏蔽，次日上午再次发送
     */
    private Integer shieldType;

    /**
     * 一个批次发送数量（默认1000）
     */
    private Integer batchCount;

    /**
     * 消息模板Id
     * <>任务ID</>
     */
    private Long messageTemplateId;

    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 去重类型
     */
    private Integer deduplicationType;

    /**
     * 发送参数：
     * 短信模版Id：key: templateId
     * 外呼JobId: key: callJobId
     * 短信链接参数： key:urlLink
     */
    private Map<String,String> params;

    /**
     * 用户组ID
     */
    private Long userGroupId;

    /**
     * 发送文案模型
     */
    private MessageContentModel messageContentModel;
}
