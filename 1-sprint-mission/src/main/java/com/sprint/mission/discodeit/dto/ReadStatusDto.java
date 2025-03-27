package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@NotBlank
public class ReadStatusDto {

  UUID id;
  UUID userId;
  UUID channelId;
  Instant lastReadAt;

}
