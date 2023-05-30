package com.works.utils;

import com.google.common.collect.Lists;
import com.works.enums.MessageTypeEnum;
import com.works.enums.PushChannelEnum;
import com.works.info.PushTaskInfo;

import java.util.List;

/**
 * @author DaLong
 * @date 2023/5/29 15:34
 */
public class GroupIdMappingUtils {

    public static List<String> getAllGroupIds() {
        List<String> groupIds = Lists.newArrayList();
        for (PushChannelEnum channelEnum : PushChannelEnum.values()) {
            if(PushChannelEnum.NULL.getCode().equals(channelEnum.getCode())){
                continue;
            }
            for (MessageTypeEnum typeEnum : MessageTypeEnum.values()) {
                if (MessageTypeEnum.NULL.getCode().equals(typeEnum.getCode())) {
                    continue;
                }
                groupIds.add(buildGroupId(channelEnum.getCode(), typeEnum.getCode()));
            }
        }
        return groupIds;
    }

    public static String getGroupIdByPushTaskInfo(PushTaskInfo pushTaskInfo) {
        String channelCode = PushChannelEnum.queryByType(pushTaskInfo.getPushChannelType()).getCode();
        String messageCode = MessageTypeEnum.queryByType(pushTaskInfo.getPushMessageType()).getCode();

        return buildGroupId(channelCode, messageCode);
    }

    private static String buildGroupId(String channelCode, String messageCode) {
        return channelCode + "-" + messageCode;
    }
}
