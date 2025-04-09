package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public record PublicChannelUpdateRequest(
    String newName,
    String newDescription
) {

}
