package com.sprint.mission.discodeit.service.interfacePackage;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

public interface ChannelServiceInterface {
    public void showInfoChannel(Channel channel);
    public Channel saveChannel(String nickName, User user, boolean checkPrivate);
    public void deleteChannel(Channel channel);
    public void modifyChannel(Channel channel, String newName);

}
