package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserStatus {
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean userOnOff;
}
