package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public record PublicChannelCreateRequest(
    String name,
    String description
) {

}
