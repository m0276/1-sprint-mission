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


    @Override
    public String toString() {
        String print = "";
        print = "BinaryContent{";
        if(userId != null) print += "userId=" + userId;

        if(messageId != null) print = ", createdAt=" + createdAt;
        print += '}';

        return print;
    }
}
