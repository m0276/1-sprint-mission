package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@NotBlank
public record UserStatusUpdateRequest(
    Instant newLastActiveAt
) {

}
