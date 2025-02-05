package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {
    UUID id;
    String userName;
    String password;
    String email;
    boolean containContent;

}
