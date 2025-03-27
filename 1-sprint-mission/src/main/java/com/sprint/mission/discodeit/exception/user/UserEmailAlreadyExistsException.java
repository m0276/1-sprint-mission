package com.sprint.mission.discodeit.exception.user;

import com.sprint.mission.discodeit.exception.ErrorCode;

public class UserEmailAlreadyExistsException extends UserException {

  public UserEmailAlreadyExistsException() {
    super(ErrorCode.DUPLICATE_USER_EMAIL);
  }
}