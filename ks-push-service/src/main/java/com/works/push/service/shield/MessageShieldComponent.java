package com.works.push.service.shield;

import com.works.info.PushTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/5/29 15:50
 */
@Slf4j
@Component
public class MessageShieldComponent {

    public void shield(PushTaskInfo info) {
        /**
         * 是否需要，拦截规则：晚上9点以后不在触达用户,可以通过延迟消息再次投递
         */
    }
}
