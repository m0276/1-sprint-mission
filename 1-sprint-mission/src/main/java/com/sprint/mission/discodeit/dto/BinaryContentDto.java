package com.sprint.mission.discodeit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
public class BinaryContentDto {

  UUID id;
  String fileName;
  Long size;
  String contentType;
}
