package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@NotNull
public class ReadStatusDto {

  UUID id;
  UUID userId;
  UUID channelId;
  Instant lastReadAt;

}
