package com.sprint.mission.discodeit.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.user.UserEmailAlreadyExistsException;
import com.sprint.mission.discodeit.exception.user.UserException;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.basic.BasicUserStatusService;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BasicUserService userService;

  @MockitoBean
  private BasicUserStatusService userStatusService;

  @MockitoBean
  private UserRepository repository;

  @MockitoBean
  private UserMapper userMapper;

  @Test
  void findUsers() throws Exception {

    UUID userId = UUID.randomUUID();

    UserCreateRequest request = new UserCreateRequest("testUser", "test@example.com", "password");

    UserDto userDto = new UserDto(userId, "testUser", "test@example.com", null, true);
    List<UserDto> list = new ArrayList<>();
    list.add(userDto);

    when(userService.create(any(UserCreateRequest.class), any())).thenReturn(userDto);
    when(userService.findAll()).thenReturn(list);

    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk());
  }

  @Test
  void createUserSuccess() throws Exception {
    UUID userId = UUID.randomUUID();

    UserCreateRequest request = new UserCreateRequest("testUser", "test@example.com", "password");
    String requestJson = new ObjectMapper().writeValueAsString(request);

    // Multipart 요청 데이터 생성
    MockMultipartFile userCreateRequestPart = new MockMultipartFile(
        "userCreateRequest",
        "userCreateRequest.json",
        "application/json",
        requestJson.getBytes(StandardCharsets.UTF_8)
    );

    UserDto userDto = new UserDto(userId, "testUser", "test@example.com", null, true);

    when(userService.create(any(UserCreateRequest.class), any())).thenReturn(userDto);
    when(userService.find(userId)).thenReturn(userDto);

    mockMvc.perform(multipart("/api/users")
            .file(userCreateRequestPart)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("testUser"));
  }
}


