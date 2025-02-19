package com.sprint.mission.discodeit.service.servicePackage;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.interfacePackage.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class UserService implements UserServiceInterface {
    private JCFUserRepository userRepository;
    private JCFMessageRepository messageRepository;
    private JCFChannelRepository channelRepository;
    private UserStatusService statusService;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

    @Autowired
    public UserService(JCFUserRepository userRepository, JCFMessageRepository messageRepository, JCFChannelRepository channelRepository, UserStatusService statusService) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
        this.statusService = statusService;
    }

    @Override
    public User creatUser(String nickName, String password, String email) {
        UserDto userDto = new UserDto();
        userDto.setUserName(nickName);
        userDto.setPassword(password);
        userDto.setEmail(email);
        statusService.create(userDto);
        return userRepository.createUser(userDto);
    }

    @Override
    public void modifyUser(User user , String newName) {
        if (userRepository.checkUserId(user.getId())) {
            userRepository.update(user.getId(), newName);
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            statusService.updateByUserId(dto);
            channelRepository.checkJoinChannel(user.getId(), newName);
            messageRepository.modifyUserName(user.getId(), newName);
            System.out.println("성공적으로 수정하였습니다");
            return;
        }

        System.out.println("존재하지 않는 계정입니다");
    }

    @Override
    public void deleteUser(User user) {
        if (userRepository.checkUserId(user.getId())) {
            userRepository.deleteUser(user.getId());
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            statusService.delete(dto);
            channelRepository.deleteUser(user.getId());
            messageRepository.modifyUserName(user.getId(),"UNDEFINED");
            System.out.println("성공적으로 삭제하였습니다");
            return;
        }

        System.out.println("존재하지 않는 계정입니다");
    }

    @Override
    public void showInfoUser(User user) {
        System.out.println(user.getName() + "(" + user.getId() + ")"
                + "\n생성 일자 : " + format.format(user.getCreatedAt())
                + "\n최종 수정 일자 : " + format.format(user.getUpdatedAt()));
    }
}
