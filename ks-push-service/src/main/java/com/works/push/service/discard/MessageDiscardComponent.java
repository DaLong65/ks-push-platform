package com.works.push.service.discard;

import com.works.info.PushTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/5/29 15:46
 */
@Slf4j
@Component
public class MessageDiscardComponent {

    public boolean isDiscard(PushTaskInfo info) {
        /**
         * 根据具体业务逻辑来做消息丢弃
         */
        return false;
    }
}
