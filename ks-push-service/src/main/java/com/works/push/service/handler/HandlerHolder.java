package com.works.push.service.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DaLong
 * @date 2023/3/6 11:43
 * <p>
 * Channel&Handler的映射关系
 */
@Component
public class HandlerHolder {

    private final Map<Integer, Handler> handlerMap = new HashMap<>(64);

    public void putHandler(Integer channelType, Handler handler) {
        handlerMap.put(channelType, handler);
    }

    public Handler route(Integer channelType) {
        return handlerMap.get(channelType);
    }
}
