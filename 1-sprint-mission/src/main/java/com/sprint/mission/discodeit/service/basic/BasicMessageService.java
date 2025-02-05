package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileReadStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class BasicMessageService implements MessageService {
//    private static final BasicMessageService INSTANCE = new BasicMessageService();
//    private final FileUserRepository userRepository = FileUserRepository.getInstance();
//    private final FileChannelRepository channelRepository = FileChannelRepository.getInstance();
//    private final FileMessageRepository messageRepository = FileMessageRepository.getInstance();
//
//    private BasicMessageService(){}
//
//    public static BasicMessageService getInstance() {
//        return INSTANCE;
//    }

    private final FileChannelRepository channelRepository;
    private final FileUserRepository userRepository;
    private final FileMessageRepository messageRepository;
    private final FileReadStatusRepository readStatusRepository;
//    @Autowired
//    public BasicMessageService(FileChannelRepository channelRepository, FileUserRepository userRepository, FileMessageRepository messageRepository) {
//        this.channelRepository = channelRepository;
//        this.userRepository = userRepository;
//        this.messageRepository = messageRepository;
//    }

    @Override
    public void showInfoMessage(Message message) throws IOException, ClassNotFoundException {
        System.out.println(messageRepository.getMessage(message));
    }

    @Override
    public Message saveMessage(String content, User user, Channel channel) throws IOException, ClassNotFoundException {
        readStatusRepository.createStatus(channel.getChannelId(),channelRepository.getChannel(channel).getUsers().keySet().stream().toList(),user.getId());
        MessageDto dto = new MessageDto();
        dto.setUserId(user.getId());
        dto.setUserName( userRepository.getUser(user).getName());
        dto.setChannelId(channel.getChannelId());
        dto.setChannelName(channelRepository.getChannel(channel).getName());
        dto.setText(content);

        return messageRepository.createMessage(dto);
    }

    @Override
    public void deleteMessage(Message message) throws IOException, ClassNotFoundException {
        messageRepository.deleteMessage(message.getMessageId());
    }

    @Override
    public void modifyMessage(Message message, String content) throws IOException, ClassNotFoundException {
        MessageDto dto = new MessageDto();
        dto.setUserId(message.getUserId());
        dto.setMessageId(message.getMessageId());
        dto.setText(content);
        messageRepository.modifyMessage(dto);
    }
}
