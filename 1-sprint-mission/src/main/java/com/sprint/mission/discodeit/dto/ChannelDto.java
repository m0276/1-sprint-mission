package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@NotBlank
public record ChannelDto(
    UUID id,
    ChannelType type,
    String name,
    String description,
    List<UserDto> participants,
    Instant lastMessageAt
) {

}
