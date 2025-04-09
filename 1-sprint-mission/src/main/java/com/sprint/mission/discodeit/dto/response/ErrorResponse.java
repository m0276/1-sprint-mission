package com.sprint.mission.discodeit.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private String code;
  private String message;
  private Map<String, Object> details;
  private Instant timestamp;
  String exceptionType;
  int status;
}
