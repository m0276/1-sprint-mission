package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public record UserUpdateRequest(
    String newUsername,
    String newEmail,
    String newPassword
) {

}
