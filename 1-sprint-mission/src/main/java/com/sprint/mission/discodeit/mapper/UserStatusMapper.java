package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.mapstruct.Mapper;

@Mapper
public interface UserStatusMapper {

  UserStatusDto toDto(UserStatus userStatus);
}
