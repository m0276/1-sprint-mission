package com.sprint.mission.discodeit.service.servicePackage;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    JCFUserRepository userRepository;

    @Autowired
    public AuthService(JCFUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User login(String userName, String password){
       UserDto userDto = new UserDto();
       userDto.setUserName(userName);
       userDto.setPassword(password);

       if(userRepository.find(userDto) == null){
           throw new RuntimeException();
       }

        return userRepository.find(userDto);
    }
}
