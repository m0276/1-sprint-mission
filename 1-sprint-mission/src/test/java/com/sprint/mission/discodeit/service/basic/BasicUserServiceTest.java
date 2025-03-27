package com.sprint.mission.discodeit.service.basic;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.user.UserAlreadyExistsException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class BasicUserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private BinaryContentRepository binaryContentRepository;
  @Mock
  private BinaryContentStorage binaryContentStorage;
  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private BasicUserService userService;


  @BeforeEach
  void delete() {
    userRepository.deleteAll();
  }

  @Test
  void createUser_Success() {
    UserCreateRequest request = new UserCreateRequest("name", "aabaa@example.com", "aaaabbbccc");
    BinaryContentCreateRequest request1 = new BinaryContentCreateRequest("file2", "image",
        new byte[]{});
    UserDto result = null;
    try {
      result = userService.create(request, Optional.of(request1));
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Then
    assertNotNull(result);
    assertEquals("name", result.username());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void createUser_Fail_EmailAlreadyExists() {
    // Given
    given(userRepository.existsByEmail("aabaa@example.com")).willReturn(true);

    // When & Then
    assertThrows(UserAlreadyExistsException.class, () -> {
      UserCreateRequest request = new UserCreateRequest("name", "aabaac@example.com", "aaaabbbccc");
      BinaryContentCreateRequest request1 = new BinaryContentCreateRequest("file31", "image",
          new byte[]{});

      userService.create(request, Optional.of(request1));
    });
    verify(userRepository, never()).save(any(User.class));
  }
}
