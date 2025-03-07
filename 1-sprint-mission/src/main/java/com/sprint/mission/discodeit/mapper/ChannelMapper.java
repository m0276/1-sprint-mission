package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SortComparator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class ChannelMapper {

  MessageRepository messageRepository;
  MessageMapper messageMapper;
  UserRepository userRepository;
  UserMapper userMapper;
  ReadStatusRepository readStatusRepository;

  @Mapping(source = "lastMessageAt", target = "lastMessageAt")
  @Mapping(source = "participantIds", target = "participants", qualifiedByName = "mapUserById")
  public abstract ChannelDto toDto(Channel channel, List<UUID> participantIds,
      Instant lastMessageAt);

  @Named("mapUserById")
  private List<UserDto> mapProfileById(List<UUID> profileIds) {
    List<UserDto> participants = new ArrayList<>();

    for (UUID id : profileIds) {
      participants.add(userRepository.findById(id)
          .map(userMapper::toDto)
          .orElse(null)
      );
    }
    return participants;
  }


  public ChannelDto channelToDto(Channel channel) {
    Instant lastMessageAt = messageRepository.findAllByChannelId(channel.getId())
        .stream()
        .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
        .map(Message::getCreatedAt)
        .limit(1)
        .findFirst()
        .orElse(Instant.MIN);

    List<UUID> participantIds = new ArrayList<>();
    if (channel.getType().equals(ChannelType.PRIVATE)) {
      readStatusRepository.findAllByChannelId(channel.getId())
          .stream()
          .map(ReadStatus::getUserId)
          .forEach(participantIds::add);
    }

    return toDto(channel, participantIds, lastMessageAt);
  }
}
