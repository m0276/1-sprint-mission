package com.sprint.mission;


import com.sprint.mission.service.jcf.JCFEntityServiceChannel;
import com.sprint.mission.service.jcf.JCFEntityServiceMessage;
import com.sprint.mission.service.jcf.JCFEntityServiceUser;

import java.util.UUID;

public class Main {
    private static final JCFEntityServiceUser user = JCFEntityServiceUser.getInstance();
    private static final JCFEntityServiceMessage message = JCFEntityServiceMessage.getInstance();
    private static final JCFEntityServiceChannel channel = JCFEntityServiceChannel.getInstance();

    public static void main(String[] args) {

        UUID user1 = user.createUser("Name1");
        user.showInfo(user1);

        UUID channel1 = channel.createChannel("Channel1",user1,user.getUserName(user1));
        System.out.print(user.getUserName(user1));
        channel.checkJoinChannel(user1);
        channel.showInfo(channel1);
        UUID user2 = user.createUser("Name2");
        channel.joinChannel(channel1,user2, user.getUserName(user2));

        UUID channel2 = channel.createChannel("Channel2",user2,user.getUserName(user2));
        System.out.print(user.getUserName(user2));
        channel.checkJoinChannel(user2);

        channel.deleteChannel(channel2);
        System.out.print(user.getUserName(user2));
        channel.checkJoinChannel(user2);

        UUID message1 = message.createMessage("Hi",user1,user.getUserName(user1),channel1);
        UUID message2 = message.createMessage("Hello",user1, user.getUserName(user1), channel1);
        message.update(user1,message1,"Hello World!");
        System.out.println(message.getMessagesOfChannel(channel1));

        message.deleteMessage(message2);
        System.out.println(message.getMessagesOfChannel(channel1));
        message.showInfo(message1);

        user.update(user1,"ReName1");
        message.updateUserName(user1, user.getUserName(user1));
        System.out.println(message.getMessagesOfChannel(channel1));

        channel.update(channel1, "ReNameChannel1");
        channel.showInfo(channel1);

        UUID testUnknownUser = UUID.randomUUID();
        System.out.println(user.getUserName(testUnknownUser));

        UUID testUnknownChannel = UUID.randomUUID();
        channel.showInfo(testUnknownChannel);

        UUID testUnknownMessage = UUID.randomUUID();
        message.showInfo(testUnknownMessage);
    }
}