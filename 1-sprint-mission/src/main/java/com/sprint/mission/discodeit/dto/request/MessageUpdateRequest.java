package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public record MessageUpdateRequest(
    String newContent
) {

}
