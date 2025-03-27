package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public record LoginRequest(
    String username,
    String password
) {

}
