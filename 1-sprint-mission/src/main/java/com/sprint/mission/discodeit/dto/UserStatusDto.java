package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@NotBlank
public class UserStatusDto {

  UUID id;
  UUID userId;
  Instant lastActiveAt;

}
