package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@NotBlank
public record PrivateChannelCreateRequest(
    List<UUID> participantIds
) {

}
