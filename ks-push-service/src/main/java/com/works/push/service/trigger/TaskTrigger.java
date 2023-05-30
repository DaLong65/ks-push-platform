package com.works.push.service.trigger;

import com.dtp.core.thread.DtpExecutor;
import com.works.push.service.thread.HandlerThreadPoolConfig;
import com.works.push.service.thread.ThreadPoolManageHandler;
import com.works.utils.GroupIdMappingUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author DaLong
 * @date 2023/5/29 15:30
 */
@Component
public class TaskTrigger {

    @Resource
    private ThreadPoolManageHandler threadPoolManageHandler;

    private final Map<String, ExecutorService> taskTriggeringHolder = new HashMap<>(32);

    private static final List<String> GROUP_ID = GroupIdMappingUtils.getAllGroupIds();

    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     * 动态线程池通过 Apollo 可修改配置
     */
    @PostConstruct
    public void init(){
        for (String groupId : GROUP_ID) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            threadPoolManageHandler.register(executor);
            taskTriggeringHolder.put(groupId, executor);
        }
    }
    /**
     * 得到对应的线程池
     */
    public ExecutorService route(String groupId) {
        return taskTriggeringHolder.get(groupId);
    }
}
