package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class BinaryContent {
    private UUID userId;
    private UUID messageId;
    private Instant createdAt;
    //private boolean contain;


}
