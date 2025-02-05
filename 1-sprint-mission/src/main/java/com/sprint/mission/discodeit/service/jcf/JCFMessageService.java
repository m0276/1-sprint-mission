package com.sprint.mission.discodeit.service.jcf;


import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.jcf.interfacePac.JCFMessageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
public class JCFMessageService implements JCFMessageServiceInterface {

    private JCFMessageRepository messageRepository;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

    @Autowired
    public JCFMessageService(JCFMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //    private static final JCFServiceJCF INSTANCE = new JCFServiceJCF();
//    private JCFServiceJCF(){}
//
//    public static JCFServiceJCF getInstance() {
//        return INSTANCE;
//    }
    @Override
    public Message saveMessage(String content, User user, Channel channel) {
        Message message = messageRepository.createMessage(content,user.getId(),
                user.getName(),channel.getChannelId(), channel.getName());

        if(messageRepository.checkMessageContains(message.getMessageId())){
            while(!messageRepository.checkMessageContains(message.getMessageId())){
                message.setMessageId(UUID.randomUUID());
            }
        }
        System.out.println("메세지 전송 성공!");
        return message;
    }

    @Override
    public void deleteMessage(Message message) {
        if(messageRepository.checkMessageContains(message.getMessageId())){
            messageRepository.deleteMessage(message.getMessageId());
            System.out.println("메세지 삭제 성공!");
        }

        System.out.println("메세지 삭제에 실패하였습니다");
    }

    @Override
    public void modifyMessage(Message message, String content) {
        if(messageRepository.checkMessageContains(message.getMessageId())){
            messageRepository.modifyMessage(message.getUserId(),message.getMessageId(),content);
            System.out.println("메세지 수정 성공!");
            return;
        }

        System.out.println("메세지 수정에 실패하였습니다");
    }

    @Override
    public void showInfoMessage(Message message) {
        System.out.println(message.getText() + " (" + message.getUserName() + ") [" +
                format.format(message.getUpdatedAt()) + "]" + " - " + message.getChannelName());
    }
}