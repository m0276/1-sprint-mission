package com.sprint.mission.discodeit.service.basic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.time.Instant;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class BasicMessageServiceTest {

  @Mock
  private MessageRepository messageRepository;

  @Mock
  private ChannelRepository channelRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private MessageMapper messageMapper;

  @Mock
  private BinaryContentStorage binaryContentStorage;

  @Mock
  private BinaryContentRepository binaryContentRepository;

  @Mock
  private PageResponseMapper pageResponseMapper;

  @InjectMocks
  private BasicMessageService messageService;

  private UUID messageId;
  private UUID channelId;
  private UUID authorId;
  private Message message;
  private MessageDto messageDto;
  private Channel channel;
  private User author;
  private UserDto userDto;

  @BeforeEach
  void setUp() {
    messageId = UUID.randomUUID();
    channelId = UUID.randomUUID();
    authorId = UUID.randomUUID();

    channel = new Channel();
    channel.setId(channelId);

    author = new User("testUser", "test@example.com", "pass", null);
    author.setId(authorId);

    message = new Message("Test message", channel, author, List.of());
    message.setId(messageId);
    userDto = new UserDto(authorId, "testUser", "test@example.com", null, true);

    messageDto = new MessageDto(messageId, Instant.now(), Instant.now(), "Test message", channelId,
        userDto, List.of());
  }

  /**
   * 1. 메시지 생성 테스트
   */
  @Test
  void A() {
    MessageCreateRequest request = new MessageCreateRequest("Hello", channelId, authorId);
    when(messageMapper.toDto(any(Message.class))).thenReturn(messageDto);
    when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
    when(userRepository.findById(authorId)).thenReturn(Optional.of(author));
    when(messageRepository.save(any(Message.class))).thenReturn(message);

    MessageDto result = messageService.create(request, List.of());

    assertNotNull(result);
    assertEquals("Test message", result.content());
    verify(messageRepository, times(1)).save(any(Message.class));
  }

  /**
   * 2. 메시지 조회 테스트
   */
  @Test
  void B() {
    when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));
    when(messageMapper.toDto(any(Message.class))).thenReturn(messageDto);
    MessageDto result = messageService.find(messageId);

    assertNotNull(result);
    assertEquals(messageId, result.id());
    verify(messageRepository, times(1)).findById(messageId);
  }

  /**
   * 5. 메시지 업데이트 테스트
   */
  @Test
  void D() {
    MessageUpdateRequest request = new MessageUpdateRequest("Updated content");
    when(messageMapper.toDto(any(Message.class))).thenReturn(messageDto);
    when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

    MessageDto result = messageService.update(messageId, request);

    assertNotNull(result);
    assertEquals("Updated content", message.getContent());
    verify(messageRepository, times(1)).findById(messageId);
  }

  /**
   * 6. 메시지 삭제 테스트
   */
  @Test
  void E() {
    when(messageRepository.existsById(messageId)).thenReturn(true);

    messageService.delete(messageId);

    verify(messageRepository, times(1)).deleteById(messageId);
  }

}
