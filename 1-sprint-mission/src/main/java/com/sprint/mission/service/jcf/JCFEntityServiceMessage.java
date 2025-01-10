package com.sprint.mission.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.service.EntityService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JCFEntityServiceMessage implements EntityService{
    private static JCFEntityServiceMessage INSTANCE = new JCFEntityServiceMessage();
    private JCFEntityServiceMessage(){}

    public static JCFEntityServiceMessage getInstance() {
        return INSTANCE;
    }

    private final List<Message> messages = new ArrayList<>();


    public UUID createMessage(String text, UUID userId, String userName ,UUID channelId) {
        Message message = new Message();
        message.setMessageId(UUID.randomUUID());
        message.setText(text);
        message.setCreatedAt(System.currentTimeMillis());
        message.setUpdatedAt(System.currentTimeMillis());
        message.setUserId(userId);
        message.setUserName(userName);
        message.setChannelId(channelId);
        messages.add(message);
        return message.getMessageId();
    }

    public void updateUserName(UUID userId, String userName){
        for(Message message : messages){
            if(message.getUserId().equals(userId)){
                message.setUserName(userName);
            }
        }
    }

    public void update(UUID Userid, UUID messageId ,String text) {
        for (Message message : messages) {
            if (message.getUserId().equals(Userid) && message.getMessageId().equals(messageId)) {
                message.setText(text);
                message.setUpdatedAt(System.currentTimeMillis());
                break;
            }
        }
    }

    public List<Message> getMessagesOfChannel(UUID channelId) {
        return messages.stream()
                .filter(x -> x.getChannelId().equals(channelId))
                .toList();
    }

    public void deleteMessage(UUID messageId){
        for(Message message : messages){
            if(message.getMessageId().equals(messageId)){
                messages.remove(message);
                break;
            }
        }
    }

    @Override
    public void showInfo(UUID messageId){
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

        for(Message message : messages){
            if(message.getMessageId().equals(messageId)){
                System.out.println(message.getText() +" " + message.getUserName() + " " + format.format(message.getUpdatedAt()));
            }
        }
    }
}
