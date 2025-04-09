package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@NotNull
public class UserStatusDto {

  UUID id;
  UUID userId;
  Instant lastActiveAt;

}
