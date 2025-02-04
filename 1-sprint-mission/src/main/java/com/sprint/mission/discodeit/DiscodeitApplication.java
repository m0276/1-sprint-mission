package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.basic.ChannelService;
import com.sprint.mission.discodeit.service.basic.MessageService;
import com.sprint.mission.discodeit.service.basic.UserService;
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
        User user = setupUser(userService);
        Channel channel = setupChannel(channelService);
        // 테스트
        messageCreateTest(messageService, channel, user);

	}

	private static User setupUser (UserService userService) throws IOException, ClassNotFoundException {
        return userService.saveUser();
    }
    private static Channel setupChannel (ChannelService channelService) throws IOException, ClassNotFoundException {
        return channelService.saveChannel();
    }
    private static void messageCreateTest (MessageService messageService,Channel channel, User user) throws IOException, ClassNotFoundException {
        messageService.saveMessage("no content",user,channel);
    }

}
