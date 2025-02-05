package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.servicePackage.ChannelService;
import com.sprint.mission.discodeit.service.servicePackage.MessageService;
import com.sprint.mission.discodeit.service.servicePackage.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DiscodeitApplication {
	static UserService userService;
	static ChannelService channelService;
	static MessageService messageService;

	@Autowired
	public DiscodeitApplication(UserService userService, ChannelService channelService, MessageService messageService) {
		DiscodeitApplication.userService = userService;
		DiscodeitApplication.channelService = channelService;
		DiscodeitApplication.messageService = messageService;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		SpringApplication.run(DiscodeitApplication.class, args);

		// 셋업
        User user = setupUser("user1","password1234","email@gmail.com");
        Channel channel = setupChannel("channel1",user,false);
        // 테스트
        messageCreateTest(messageService, channel, user);

	}

	private static User setupUser (String nickName,String password, String email) throws IOException, ClassNotFoundException {
        return userService.creatUser(nickName,password,email);
    }
    private static Channel setupChannel (String channelName,User user,boolean privateCheck) throws IOException, ClassNotFoundException {
        return channelService.saveChannel(channelName,user,privateCheck);
    }
    private static void messageCreateTest (MessageService messageService,Channel channel, User user) throws IOException, ClassNotFoundException {
        messageService.saveMessage("no content",user,channel,false,0);
    }

}
