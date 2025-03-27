package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {BinaryContentMapper.class, UserMapper.class})
public abstract class MessageMapper {

  BinaryContentMapper binaryContentMapper;

  @Autowired
  public MessageMapper(BinaryContentMapper binaryContentMapper) {
    this.binaryContentMapper = binaryContentMapper;
  }

  public MessageMapper() {
  }

  @Mapping(target = "channelId", expression = "java(message.getChannel().getId())")
  @Mapping(target = "attachments", expression = "java(binaryContentToDto(message))")
  abstract public MessageDto toDto(Message message);

  protected List<BinaryContentDto> binaryContentToDto(Message message) {
    return (message.getAttachments().stream().map(binaryContentMapper::toDto)
        .collect(Collectors.toList()));
  }
}
