package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
@RequiredArgsConstructor
@Service
public class BasicUserService implements UserService{
//    private static final BasicUserService INSTANCE = new BasicUserService();
//    private final FileUserRepository userRepository = FileUserRepository.getInstance();
//    private final FileChannelRepository channelRepository = FileChannelRepository.getInstance();
//    private final FileMessageRepository messageRepository = FileMessageRepository.getInstance();
//
//    private BasicUserService(){}
//
//    public static BasicUserService getInstance() {
//        return INSTANCE;
//    }

    private final FileChannelRepository channelRepository;
    private final FileUserRepository userRepository;
    private final FileMessageRepository messageRepository;

//    @Autowired
//    public BasicUserService(FileChannelRepository channelRepository, FileUserRepository userRepository, FileMessageRepository messageRepository) {
//        this.channelRepository = channelRepository;
//        this.userRepository = userRepository;
//        this.messageRepository = messageRepository;
//    }

    @Override
    public void showInfoUser(User user) throws IOException, ClassNotFoundException {
        System.out.println((userRepository.getUser(user)));
    }

    @Override
    public User saveUser() throws IOException, ClassNotFoundException{
        return userRepository.createUser("UnNamed");
    }

    @Override
    public void deleteUser(User user) throws IOException, ClassNotFoundException {
        userRepository.deleteUser(user.getId());
        channelRepository.deleteUser(user.getId());
        messageRepository.modifyUserName(user.getId(),"Undefined");
    }

    @Override
    public void modifyUser(User user, String newName) throws IOException, ClassNotFoundException {
        userRepository.modifyUser(user.getId(),newName);
        if(!channelRepository.getChannels().isEmpty()){
            channelRepository.modifyChannelUserName(user.getId(),newName);
        }
        if(!messageRepository.getMessages().isEmpty()){
            messageRepository.modifyUserName(user.getId(),newName);
        }
    }
}
