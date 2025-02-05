package com.sprint.mission.discodeit.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChannelDto {
    boolean channelPrivate;
    String channelName;
    UUID userId;
    String userName;
    UUID channelId;
}
