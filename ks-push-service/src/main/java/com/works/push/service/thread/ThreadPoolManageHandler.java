package com.works.push.service.thread;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import com.works.common.CommonConstants;
import org.springframework.stereotype.Component;

/**
 * @author DaLong
 * @date 2023/5/29 15:31
 */
@Component
public class ThreadPoolManageHandler {

    public void register(DtpExecutor dtpExecutor){
        DtpRegistry.registerDtp(dtpExecutor, CommonConstants.SERVICE_NAME);
    }
}
