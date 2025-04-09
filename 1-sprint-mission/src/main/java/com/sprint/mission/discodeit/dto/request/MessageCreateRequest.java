package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@NotBlank
public record MessageCreateRequest(
    String content,
    UUID channelId,
    UUID authorId
) {

}
