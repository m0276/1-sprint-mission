//package com.sprint.mission.discodeit.configure;
//
//import com.sprint.mission.discodeit.repository.file.*;
//import com.sprint.mission.discodeit.service.basic.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class Configure {
//
//    @Bean
//    public FileUserRepository fileUserRepository(){
//        return new FileUserRepository();
//    }
//
//    @Bean
//    public FileChannelRepository fileChannelRepository(){
//        return new FileChannelRepository();
//    }
//
//    @Bean
//    public FileMessageRepository fileMessageRepository(){
//        return new FileMessageRepository();
//    }
//
//    @Bean
//    public UserService userService(){
//        return new BasicUserService(fileChannelRepository(),fileUserRepository(),fileMessageRepository());
//    }
//
//    @Bean
//    public ChannelService channelService(){
//        return new BasicChannelService(fileChannelRepository(),fileUserRepository(),fileMessageRepository());
//
//    }
//
//    @Bean
//    public MessageService messageService(){
//        return new BasicMessageService(fileChannelRepository(),fileUserRepository(),fileMessageRepository());
//    }
//}
