package com.sprint.mission.discodeit.exception.channel;

import com.sprint.mission.discodeit.exception.ErrorCode;

public class PrivateChannelAccessDeniedException extends ChannelException {

  public PrivateChannelAccessDeniedException(Long channelId) {
    super(ErrorCode.CHANNEL_PRIVATE);
  }
}
