package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class BinaryContentDto {

  UUID id;
  String fileName;
  Long size;
  String contentType;
}
