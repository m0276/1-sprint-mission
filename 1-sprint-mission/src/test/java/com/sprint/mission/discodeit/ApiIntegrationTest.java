package com.sprint.mission.discodeit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.basic.BasicUserStatusService;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ApiIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  public void deleteRepository() {
    messageRepository.deleteAll();
    userStatusRepository.deleteAll();
    readStatusRepository.deleteAll();
    channelRepository.deleteAll();
    userRepository.deleteAll();
  }


  @Test
  @Transactional
  public void createUserTest() throws Exception {
    UserCreateRequest request = new UserCreateRequest("testUser", "test@example.com", "password");
    String requestJson = new ObjectMapper().writeValueAsString(request);

    MockMultipartFile userCreateRequestPart = new MockMultipartFile(
        "userCreateRequest",
        "userCreateRequest.json",
        MediaType.APPLICATION_JSON_VALUE,
        requestJson.getBytes(StandardCharsets.UTF_8)
    );

    MockMultipartFile profileFile = new MockMultipartFile(
        "profile",
        "profile.jpg",
        "image/jpeg",
        "sample profile content".getBytes()
    );

    mockMvc.perform(multipart("/api/users")
            .file(userCreateRequestPart)
            .file(profileFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("testUser"));

  }


  @Test
  @Transactional
  @Rollback(false)
  public void getUserListTest() throws Exception {
    UserCreateRequest request = new UserCreateRequest("testUser", "test@example.com", "password");
    String requestJson = new ObjectMapper().writeValueAsString(request);

    MockMultipartFile userCreateRequestPart = new MockMultipartFile(
        "userCreateRequest",
        "userCreateRequest.json",
        MediaType.APPLICATION_JSON_VALUE,
        requestJson.getBytes(StandardCharsets.UTF_8)
    );

    MockMultipartFile profileFile = new MockMultipartFile(
        "profile",
        "profile.jpg",
        "image/jpeg",
        "sample profile content".getBytes()
    );

    mockMvc.perform(multipart("/api/users")
        .file(userCreateRequestPart)
        .file(profileFile)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));

    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].username").value("testUser"));
  }

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private ReadStatusRepository readStatusRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  @Transactional
  public void createChannelTest() throws Exception {
    // Given
    PublicChannelCreateRequest channelCreateRequest = new PublicChannelCreateRequest("Test Channel",
        "description");
    String requestJson = new ObjectMapper().writeValueAsString(channelCreateRequest);

    // When
    mockMvc.perform(post("/api/channels/public")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Test Channel"));
  }

  @Test
  @Transactional
  @Rollback(value = false)
  public void deleteChannelTest() throws Exception {
    // Given
    Channel channel = new Channel(ChannelType.PUBLIC, "Channel to delete", "description");
    channelRepository.save(channel);
    channelRepository.flush();

    User user = new User("user1", "email@email.com", "password", null);
    userRepository.save(user);
    userRepository.flush();

    ReadStatus readStatus = new ReadStatus(user, channel, Instant.now());
    readStatusRepository.save(readStatus);
    readStatusRepository.flush();

    // When
    mockMvc.perform(delete("/api/channels/{id}", channel.getId()))
        .andExpect(status().isNoContent());

    // Then
    mockMvc.perform(get("/api/channels?userId=" + readStatus.getUser().getId()))
        .andExpect(jsonPath("$").isEmpty());
  }

  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private UserStatusRepository userStatusRepository;

  @Test
  @Transactional
  @Rollback(value = false)
  public void createMessageTest() throws Exception {
    // Given
    Channel channel = new Channel(ChannelType.PUBLIC, "Channel to delete", "description");
    channelRepository.save(channel);
    channelRepository.flush();

    User user = new User("user1", "email@email.com", "password", null);
    userRepository.save(user);
    userRepository.flush();

    UserStatus userStatus = new UserStatus(user, Instant.now());
    userStatusRepository.save(userStatus);
    userStatusRepository.flush();

    MessageCreateRequest messageCreateRequest = new MessageCreateRequest("Test message content",
        channel.getId(), user.getId());
    String requestJson = new ObjectMapper().writeValueAsString(messageCreateRequest);

    MockMultipartFile messageCreateRequestPart = new MockMultipartFile(
        "messageCreateRequest",
        "message.json",
        MediaType.APPLICATION_JSON_VALUE,
        requestJson.getBytes(StandardCharsets.UTF_8)
    );

    MockMultipartFile attachment = new MockMultipartFile(
        "attachments",
        "image1.jpg",
        "image/jpeg",
        "FakeImageContent1".getBytes()
    );

    // When
    mockMvc.perform(multipart("/api/messages")
            .file(messageCreateRequestPart)
            .file(attachment)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.content").value("Test message content"));
  }

  @Test
  @Transactional
  @Rollback(value = false)
  public void getMessageListTest() throws Exception {
    // Given
    Channel channel = new Channel(ChannelType.PUBLIC, "Channel to delete", "description");
    channelRepository.save(channel);
    channelRepository.flush();

    User user = new User("user1", "email@email.com", "password", null);
    userRepository.save(user);
    userRepository.flush();

    UserStatus userStatus = new UserStatus(user, Instant.now());
    userStatusRepository.save(userStatus);
    userStatusRepository.flush();

    Message message = new Message("Test message content",
        channel, user, Collections.emptyList());
    messageRepository.save(message);

    // When
    mockMvc.perform(get("/api/messages?channelId=" + channel.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].content").value("Test message content"));
  }
}
