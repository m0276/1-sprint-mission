package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotNull;

@NotNull
public record MessageUpdateRequest(
    String newContent
) {

}
