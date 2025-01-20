package com.sprint.mission;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.file.FileService;
import com.sprint.mission.discodeit.service.jcf.JCFEntityService;

import java.io.IOException;

public class JavaApplicationMain {
    static JCFEntityService jcfEntityService = JCFEntityService.getInstance();
    static FileService fileService = FileService.getInstance();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user1 = fileService.saveUser("Name1");
        fileService.showInfoUser(user1);

        System.out.println(" ");
        fileService.modifyUser(user1,"ReName1");
        fileService.showInfoUser(user1);

        System.out.println(" ");
        Channel channel1 = fileService.saveChannel("Channel1",user1);
        fileService.showInfoChannel(channel1);

        System.out.println(" ");
        Message message1 = fileService.saveMessage("Hello World!", user1,channel1);
        fileService.showInfoMessage(message1);

        fileService.modifyChannel(channel1,"RenameChannel1");

        System.out.println("\n");
        User user2 = fileService.saveUser("Name2");
        fileService.showInfoUser(user2);

        fileService.joinChannel(channel1,user2);
        fileService.deleteUser(user1);

        System.out.println("\n");
        fileService.showInfoChannel(channel1);

        fileService.modifyMessage(message1,"Hello World!!");

        System.out.println("\n");
        fileService.showInfoMessage(message1);

        fileService.deleteChannel(channel1);

    }
}
