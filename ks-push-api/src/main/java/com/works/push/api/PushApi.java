package com.works.push.api;

import com.works.dto.DubboResponse;
import com.works.push.api.requset.PushRequest;
import com.works.push.api.result.PushResult;

/**
 * @author DaLong
 * @date 2023/5/29 11:45
 */
public interface PushApi {

    DubboResponse<PushResult> push(PushRequest request);
}
