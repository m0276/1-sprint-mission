package com.sprint.mission.discodeit.service.jcf.interfacePac;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

public interface JCFMessageServiceInterface {


    public void showInfoMessage(Message message);
    public Message saveMessage(String content, User user, Channel channel);
    public void deleteMessage(Message message);
    public void modifyMessage(Message message, String content);
}
