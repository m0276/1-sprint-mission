package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessageDto {
    boolean haveContent;
    String text;
    UUID channelId;
    String channelName;
    UUID messageId;
    UUID userId;
    String userName;
    int howManyContent;
}
