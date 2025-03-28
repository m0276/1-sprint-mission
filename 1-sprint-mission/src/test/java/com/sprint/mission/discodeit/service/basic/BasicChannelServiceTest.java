package com.sprint.mission.discodeit.service.basic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicChannelServiceTest {

  @Mock
  private ChannelRepository channelRepository;

  @Mock
  private ReadStatusRepository readStatusRepository;

  @Mock
  private MessageRepository messageRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ChannelMapper channelMapper;

  @InjectMocks
  private BasicChannelService channelService;

  private UUID channelId;
  private UUID userId;
  private Channel channel;
  private ChannelDto channelDto;

  @BeforeEach
  void setUp() {
    channelId = UUID.randomUUID();
    userId = UUID.randomUUID();
    channel = new Channel(ChannelType.PUBLIC, "Test Channel", "Test Description");
    channel.setId(channelId);

    channelDto = new ChannelDto(channelId, ChannelType.PUBLIC, "Test Channel", "Test Description",
        new ArrayList<>(), Instant.now());


  }

  /**
   * 1. 공개 채널 생성 테스트
   */
  @Test
  void A() {
    when(channelMapper.toDto(any(Channel.class))).thenReturn(channelDto);
    PublicChannelCreateRequest request = new PublicChannelCreateRequest("Test Channel",
        "Test Description");

    when(channelRepository.save(any(Channel.class))).thenAnswer(invocation -> {
      Channel savedChannel = invocation.getArgument(0);
      savedChannel.setId(channelId); // Mock ID 할당
      return savedChannel;
    });

    ChannelDto result = channelService.create(request);

    assertNotNull(result);
    assertEquals("Test Channel", result.name());
    verify(channelRepository, times(1)).save(any(Channel.class));
  }

  /**
   * 2. 비공개 채널 생성 테스트
   */
  @Test
  void B() {
    when(channelMapper.toDto(any(Channel.class))).thenReturn(channelDto);
    PrivateChannelCreateRequest request = new PrivateChannelCreateRequest(List.of(userId));

    when(channelRepository.save(any(Channel.class))).thenReturn(channel);
    when(userRepository.findAllById(any())).thenReturn(
        List.of(new User("testUser", "test@example.com", "password", null)));

    ChannelDto result = channelService.create(request);

    assertNotNull(result);
    verify(channelRepository, times(1)).save(any(Channel.class));
    verify(readStatusRepository, times(1)).saveAll(any());
  }

  /**
   * 6. 공개 채널 업데이트 테스트
   */
  @Test
  void C() {
    when(channelMapper.toDto(any(Channel.class))).thenReturn(channelDto);
    PublicChannelUpdateRequest request = new PublicChannelUpdateRequest("New Name",
        "New Description");

    when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));

    ChannelDto result = channelService.update(channelId, request);

    assertNotNull(result);
    assertEquals("New Name", channel.getName());
    verify(channelRepository, times(1)).findById(channelId);
  }

  /**
   * 8. 채널 삭제 테스트
   */
  @Test
  void D() {
    when(channelRepository.existsById(channelId)).thenReturn(true);

    channelService.delete(channelId);

    verify(messageRepository, times(1)).deleteAllByChannelId(channelId);
    verify(readStatusRepository, times(1)).deleteAllByChannelId(channelId);
    verify(channelRepository, times(1)).deleteById(channelId);
  }
}
