package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@NotBlank
public record ReadStatusCreateRequest(
    UUID userId,
    UUID channelId,
    Instant lastReadAt
) {

}
