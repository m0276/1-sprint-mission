package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@RequiredArgsConstructor
@Mapper(componentModel = "spring", uses = {UserMapper.class, BinaryContentMapper.class})
public abstract class MessageMapper {

  BinaryContentMapper binaryContentMapper;
  BinaryContentRepository binaryContentRepository;

  @Mapping(source = "attachmentIds", target = "attachments", qualifiedByName = "mapBinaryContents")
  abstract MessageDto toDto(Message message);

  @Named("mapBinaryContents")
  List<BinaryContentDto> mapBinaryContents(List<UUID> attachmentIds) {
    List<BinaryContentDto> list = new ArrayList<>();

    for (UUID id : attachmentIds) {
      list.add(binaryContentRepository.findById(id)
          .map(binaryContentMapper::toDto)
          .orElse(null));
    }

    return list;
  }

}
