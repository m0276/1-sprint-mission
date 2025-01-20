package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.io.IOException;

public interface MessageService {
    public void showInfoMessage(Message message) throws IOException, ClassNotFoundException;
    public Message saveMessage(String content, User user, Channel channel) throws IOException, ClassNotFoundException;
    public void deleteMessage(Message message) throws IOException, ClassNotFoundException;
    public void modifyMessage(Message message, String content) throws IOException, ClassNotFoundException;
}
