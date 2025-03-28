package com.sprint.mission.discodeit.service.basic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.channel.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.message.MessageNotFoundException;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class BasicMessageServiceTest {

  @Mock
  private MessageRepository messageRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private ChannelRepository channelRepository;
  @Mock
  private MessageMapper messageMapper;
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
    author = new User("testUser", "test@example.com", "pass", null);
    userDto = new UserDto(authorId, "testUser", "test@example.com", null, true);

    message = new Message("Hello", channel, author, null);
    messageDto = new MessageDto(messageId, Instant.now(), Instant.now(), "Hello", channelId,
        userDto, new ArrayList<>()
    );
  }

  @Test
  void createMessage_ShouldSucceed_WhenValidRequest() {
    // given
    MessageCreateRequest request = new MessageCreateRequest("Hello", channelId, authorId);

    given(channelRepository.findById(channelId)).willReturn(Optional.of(channel));
    given(userRepository.findById(authorId)).willReturn(Optional.of(author));
    given(messageRepository.save(any(Message.class))).willReturn(message);
    given(messageMapper.toDto(any(Message.class))).willReturn(messageDto);

    // when
    MessageDto result = messageService.create(request, List.of());

    // then
    assertThat(result.content()).isEqualTo("Hello");
    then(messageRepository).should().save(any(Message.class));
  }

  @Test
  void createMessage_ShouldFail_WhenChannelNotFound() {
    // given
    MessageCreateRequest request = new MessageCreateRequest("Hello", channelId, authorId);

    given(channelRepository.findById(channelId)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> messageService.create(request, List.of()))
        .isInstanceOf(ChannelNotFoundException.class)
        .hasMessageContaining("채널을 찾을 수 없습니다.");
  }

  @Test
  void updateMessage_ShouldSucceed_WhenMessageExists() {
    // given
    MessageUpdateRequest request = new MessageUpdateRequest("Updated Message");

    given(messageRepository.findById(messageId)).willReturn(Optional.of(message));
    given(messageMapper.toDto(any(Message.class))).willReturn(
        new MessageDto(messageId, Instant.now(), Instant.now(), "Updated Message", channelId,
            userDto, new ArrayList<>()));

    // when
    MessageDto result = messageService.update(messageId, request);

    // then
    assertThat(result.content()).isEqualTo("Updated Message");
    then(messageRepository).should().findById(messageId);
  }

  @Test
  void updateMessage_ShouldFail_WhenMessageNotFound() {
    // given
    given(messageRepository.findById(messageId)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> messageService.update(messageId, new MessageUpdateRequest("Updated")))
        .isInstanceOf(MessageNotFoundException.class)
        .hasMessageContaining("메세지를 찾을 수 없습니다.");
  }

  @Test
  void deleteMessage_ShouldSucceed_WhenMessageExists() {
    // given
    given(messageRepository.existsById(messageId)).willReturn(true);
    willDoNothing().given(messageRepository).deleteById(messageId);

    // when
    messageService.delete(messageId);

    // then
    then(messageRepository).should().deleteById(messageId);
  }

  @Test
  void deleteMessage_ShouldFail_WhenMessageNotFound() {
    // given
    given(messageRepository.existsById(messageId)).willReturn(false);

    // when & then
    assertThatThrownBy(() -> messageService.delete(messageId))
        .isInstanceOf(MessageNotFoundException.class)
        .hasMessageContaining("메세지를 찾을 수 없습니다.");

    then(messageRepository).should(never()).deleteById(any(UUID.class));
  }

  @Test
  void findMessagesByChannelId_ShouldReturnMessages() {
    // given
    Pageable pageable = Pageable.ofSize(10);
    Instant now = Instant.now();

    given(messageRepository.findAllByChannelIdWithAuthor(channelId, now, pageable))
        .willReturn(new SliceImpl<>(List.of(message), pageable, false));
    given(messageMapper.toDto(any(Message.class))).willReturn(messageDto);
    given(pageResponseMapper.fromSlice(any(), any())).willReturn(
        new PageResponse<>(List.of(messageDto), null, 10, false, 100L));

    // when
    PageResponse<MessageDto> response = messageService.findAllByChannelId(channelId, now, pageable);

    // then
    assertThat(response.content()).hasSize(1);
    assertThat(response.content().get(0).content()).isEqualTo("Hello");
  }
}
