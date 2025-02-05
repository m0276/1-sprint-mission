//package com.sprint.mission.discodeit.service.basic;
//
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
//import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
//import com.sprint.mission.discodeit.repository.file.FileUserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.text.Format;
//import java.text.SimpleDateFormat;
//
//@RequiredArgsConstructor
//@Service
//public class BasicChannelService implements ChannelService {
////    private static final BasicChannelService INSTANCE = new BasicChannelService();
////    private final FileUserRepository userRepository = FileUserRepository.getInstance();
////    private final FileChannelRepository channelRepository = FileChannelRepository.getInstance();
////    private final FileMessageRepository messageRepository = FileMessageRepository.getInstance();
////
////    private BasicChannelService(){}
////
////    public static BasicChannelService getInstance() {
////        return INSTANCE;
////    }
//    private final FileChannelRepository channelRepository;
//    private final FileUserRepository userRepository;
//    private final FileMessageRepository messageRepository;
//
////    @Autowired
////    public BasicChannelService(FileChannelRepository channelRepository, FileUserRepository userRepository, FileMessageRepository messageRepository) {
////        this.channelRepository = channelRepository;
////        this.userRepository = userRepository;
////        this.messageRepository = messageRepository;
////    }
//
//    @Override
//    public void showInfoChannel(Channel channel) throws IOException, ClassNotFoundException {
//        if(channelRepository.getChannel(channel) == null) System.out.println("없는 서버입니다");
//        else System.out.println(channelRepository.getChannel(channel));
//    }
//
//    @Override
//    public Channel saveChannel() throws IOException, ClassNotFoundException {
//        return channelRepository.createChannel("Channel1",new User());
//    }
//
//    @Override
//    public void deleteChannel(Channel channel) throws IOException, ClassNotFoundException {
//        channelRepository.deleteChannel(channel.getChannelId());
//        messageRepository.deletedChannelMessage(channel.getChannelId());
//    }
//
//    @Override
//    public void modifyChannel(Channel channel, String newName) throws IOException, ClassNotFoundException {
//        channelRepository.modifyChannel(channel.getChannelId(), newName);
//        messageRepository.modifyChannelName(channel.getChannelId(),newName);
//    }
//
//    public void joinChannel(Channel channel, User user) throws IOException, ClassNotFoundException {
//        channelRepository.joinChannel(channel.getChannelId(),userRepository.getUser(user).getId(),userRepository.getUser(user).getName());
//    }
//}
