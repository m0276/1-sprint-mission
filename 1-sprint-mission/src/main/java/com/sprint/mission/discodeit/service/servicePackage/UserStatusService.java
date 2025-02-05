package com.sprint.mission.discodeit.service.servicePackage;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.jcf.JCFUserStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatusService {
    JCFUserStatusRepository repository;

    public void create(UserDto userDto){
        repository.saveStatus(userDto.getId());
    }

    public UserStatus find(UserDto userDto){
        return repository.findByUserId(userDto);
    }

    public List<UserStatus> findAll(){
        return repository.findAll();
    }

    public void delete(UserDto userDto){
        repository.delete(userDto.getId());
    }

    public void updateByUserId(UserDto userDto){
        repository.updateStatus(userDto.getId());
    }


}
