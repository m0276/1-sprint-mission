package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@NotBlank
public record UserDto(
    UUID id,
    String username,

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    String email,
    BinaryContentDto profile,
    Boolean online
) {

}
