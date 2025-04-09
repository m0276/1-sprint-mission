package com.sprint.mission.discodeit.service.basic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.channel.ChannelNotFoundException;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
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
class BasicChannelServiceTest {

  @Mock
  ReadStatusRepository readStatusRepository;
  @Mock
  private ChannelRepository channelRepository;
  @Mock
  private ChannelMapper channelMapper;
  @Mock
  private UserRepository userRepository;
  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private BasicChannelService channelService;

  private UUID channelId;
  private UUID userId;
  private Channel channel;
  private ChannelDto channelDto;


  @BeforeEach
  void setUp() {
    channelId = UUID.randomUUID();
    channel = new Channel(ChannelType.PUBLIC, "channel", "test channel");
    channelDto = new ChannelDto(channelId, ChannelType.PUBLIC, "channel", "test channel",
        new ArrayList<>(),
        Instant.now());
    userId = UUID.randomUUID();
  }

  @Test
  void createPublicChannel_ShouldSucceed_WhenUserExists() {
    // given
    PublicChannelCreateRequest request = new PublicChannelCreateRequest("channel", "test channel");

    //given(userRepository.findById(userId)).willReturn(Optional.of(user));
    given(channelRepository.save(any(Channel.class))).willReturn(channel);
    given(channelMapper.toDto(any(Channel.class))).willReturn(channelDto);

    // when
    ChannelDto result = channelService.create(request);

    // then
    assertThat(result.name()).isEqualTo("channel");
    assertThat(result.type()).isEqualTo(ChannelType.PUBLIC);
    then(channelRepository).should().save(any(Channel.class));
  }

  @Test
  void createPrivateChannel_ShouldSucceed_WhenUserExists() {
    // given
    PrivateChannelCreateRequest request = new PrivateChannelCreateRequest(List.of(userId));

    //given(userRepository.findById(userId)).willReturn(Optional.of(user));
    given(channelRepository.save(any(Channel.class))).willReturn(channel);
    given(channelMapper.toDto(any(Channel.class))).willReturn(channelDto);

    // when
    ChannelDto result = channelService.create(request);

    // then
    assertThat(result.name()).isEqualTo("channel");
    assertThat(result.type()).isEqualTo(ChannelType.PUBLIC);
    then(channelRepository).should().save(any(Channel.class));
  }

  @Test
  void updateChannel_ShouldSucceed_WhenChannelExists() {
    // given
    PublicChannelUpdateRequest request = new PublicChannelUpdateRequest("new channel",
        "new description");

    given(channelRepository.findById(channelId)).willReturn(Optional.of(channel));
    given(channelMapper.toDto(any(Channel.class))).willReturn(channelDto);

    // when
    ChannelDto result = channelService.update(channelId, request);

    // then
    assertThat(result.name()).isEqualTo("channel");
    then(channelRepository).should().findById(channelId);
  }

  @Test
  void deleteChannel_ShouldSucceed_WhenChannelExists() {
    // given
    given(channelRepository.existsById(channelId)).willReturn(true);
    willDoNothing().given(channelRepository).deleteById(channelId);

    // when
    channelService.delete(channelId);

    // then
    then(channelRepository).should().deleteById(channelId);
  }

  @Test
  void deleteChannel_ShouldFail_WhenChannelNotFound() {
    // given
    given(channelRepository.existsById(channelId)).willReturn(false);

    // when & then
    assertThatThrownBy(() -> channelService.delete(channelId))
        .isInstanceOf(ChannelNotFoundException.class);
  }

}
