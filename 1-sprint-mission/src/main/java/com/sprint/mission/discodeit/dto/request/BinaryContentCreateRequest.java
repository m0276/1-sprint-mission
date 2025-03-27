package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public record BinaryContentCreateRequest(
    String fileName,
    String contentType,
    byte[] bytes
) {

}
