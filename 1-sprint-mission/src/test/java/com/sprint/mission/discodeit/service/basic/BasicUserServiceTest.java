package com.sprint.mission.discodeit.service.basic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.user.UserAlreadyExistsException;
import com.sprint.mission.discodeit.exception.user.UserEmailAlreadyExistsException;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserStatusRepository userStatusRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private BinaryContentRepository binaryContentRepository;

  @Mock
  private BinaryContentStorage binaryContentStorage;

  @InjectMocks
  private BasicUserService userService;

  private UUID userId;
  private User user;
  private UserDto userDto;

  @BeforeEach
  void setUp() {
    userId = UUID.randomUUID();
    user = new User("testUser", "test@example.com", "password", null);
    user.setId(userId);

    userDto = new UserDto(userId, "testUser", "test@example.com", null, true);
  }

  /**
   * 1. 사용자 생성 테스트
   */
  @Test
  void A() {
    UserCreateRequest request = new UserCreateRequest("testUser", "test@example.com", "password");
    when(userMapper.toDto(any(User.class))).thenReturn(userDto);
    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserDto result = userService.create(request, Optional.empty());

    assertNotNull(result);
    assertEquals("testUser", result.username());
    verify(userRepository, times(1)).save(any(User.class));
  }

  /**
   * 2. 사용자 조회 테스트
   */
  @Test
  void B() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userMapper.toDto(any(User.class))).thenReturn(userDto);
    UserDto result = userService.find(userId);

    assertNotNull(result);
    assertEquals(userId, result.id());
    verify(userRepository, times(1)).findById(userId);
  }


  /**
   * 4. 모든 사용자 조회 테스트
   */
  @Test
  void D() {
    when(userRepository.findAllWithProfileAndStatus()).thenReturn(List.of(user));
    when(userMapper.toDto(any(User.class))).thenReturn(userDto);
    List<UserDto> result = userService.findAll();

    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    verify(userRepository, times(1)).findAllWithProfileAndStatus();
  }

  /**
   * 5. 사용자 업데이트 테스트
   */
  @Test
  void E() {
    UserUpdateRequest request = new UserUpdateRequest("newUser", "new@example.com", "newPassword");
    when(userMapper.toDto(any(User.class))).thenReturn(userDto);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userRepository.existsByEmail(request.newEmail())).thenReturn(false);
    when(userRepository.existsByUsername(request.newUsername())).thenReturn(false);

    UserDto result = userService.update(userId, request, Optional.empty());

    assertNotNull(result);
    assertEquals("newUser", user.getUsername());
    assertEquals("new@example.com", user.getEmail());
    verify(userRepository, times(1)).findById(userId);
  }


  /**
   * 8. 중복 이메일로 사용자 생성 시 예외 발생
   */
  @Test
  void H() {
    UserCreateRequest request = new UserCreateRequest("testUser", "test@example.com", "password");

    when(userRepository.existsByEmail(request.email())).thenReturn(true);

    assertThrows(UserEmailAlreadyExistsException.class,
        () -> userService.create(request, Optional.empty()));
  }

  
}
