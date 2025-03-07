package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.mapstruct.Mapper;

@Mapper
public interface ReadStatusMapper {

  ReadStatusDto toDto(ReadStatus readStatus);
}
