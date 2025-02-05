package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    JCFUserRepository userRepository;

    @Autowired
    public AuthService(JCFUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User login(String userName, String password){
        List<User> list = userRepository.getUsers();
        for(User user : list){
            if(user.getName().equals(userName) && user.getPassword().equals(password)){
                return user;
            }
        }

        throw new RuntimeException();
    }
}
