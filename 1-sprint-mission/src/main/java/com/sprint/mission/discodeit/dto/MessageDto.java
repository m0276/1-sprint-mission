package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import com.sprint.mission.discodeit.dto.UserDto;
import lombok.Getter;

@NotNull
public record MessageDto(

    UUID id,
    Instant createdAt,
    Instant updatedAt,
    String content,
    UUID channelId,
    UserDto author,
    List<BinaryContentDto> attachments
) {

}
