package com.sprint.mission.discodeit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.lang.*;

@Getter
public enum ErrorCode {

  // 공통 예외
  INTERNAL_SERVER_ERROR("E500", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  BAD_REQUEST("E400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
  NOT_FOUND("E404", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // 사용자 관련 예외
  USER_NOT_FOUND("U001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  DUPLICATE_USER("U002", "이미 존재하는 사용자입니다.", HttpStatus.CONFLICT),
  DUPLICATE_USER_EMAIL("U003", "이미 존재하는 이메일입니다.", HttpStatus.CONFLICT),

  // 채널 관련 예외
  CHANNEL_NOT_FOUND("C001", "채널을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  CHANNEL_PRIVATE("C002", "프라이빗 채널입니다.", HttpStatus.NOT_ACCEPTABLE),

  //메세지 관련 예외
  MESSAGE_NOT_FOUND("M001", "메세지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // 데이터베이스 관련 예외
  DATABASE_ERROR("DB001", "데이터베이스 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  DATA_NOT_FOUND("DB002", "해당 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;        // 예외 코드
  private final String message;     // 예외 메시지
  private final HttpStatus status;  // HTTP 상태 코드

  ErrorCode(String code, String message, HttpStatus status) {
    this.code = code;
    this.message = message;
    this.status = status;
  }
}
