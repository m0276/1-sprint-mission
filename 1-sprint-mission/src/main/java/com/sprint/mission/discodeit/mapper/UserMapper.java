package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {BinaryContentMapper.class, UserStatusMapper.class})
public abstract class UserMapper {

  private BinaryContentMapper binaryContentMapper;

  @Autowired
  public UserMapper(BinaryContentMapper binaryContentMapper) {
    this.binaryContentMapper = binaryContentMapper;
  }

  public UserMapper() {
  }

  @Mapping(source = "username", target = "username")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "id", target = "id")
  @Mapping(target = "profile", expression = "java(mapping(user.getProfile()))")
  @Mapping(target = "online", expression = "java(user.getStatus().isOnline())")
  abstract public UserDto toDto(User user);

  protected BinaryContentDto mapping(BinaryContent profile) {
    return binaryContentMapper.toDto(profile);
  }
}


