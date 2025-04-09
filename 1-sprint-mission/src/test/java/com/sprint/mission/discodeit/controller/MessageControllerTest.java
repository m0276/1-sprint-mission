package com.sprint.mission.discodeit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.controller.MessageController;
import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BasicMessageService messageService;  // Mocking the service layer


  private UUID messageId;
  private UUID authorId;
  private UUID channelId;
  private MessageDto messageDto;

  @BeforeEach
  void setUp() {
    messageId = UUID.randomUUID();
    channelId = UUID.randomUUID();
    authorId = UUID.randomUUID();
    UserDto userDto = new UserDto(authorId, "testUser", "test@example.com", null, true);
    messageDto = new MessageDto(messageId, Instant.now(), Instant.now(), "Message content",
        channelId,
        userDto, null);
  }

  @Test
  void createMessageSuccess() throws Exception {
    MessageCreateRequest messageCreateRequest = new MessageCreateRequest("Message content",
        channelId, authorId);

    String messageCreateRequestJson = new ObjectMapper().writeValueAsString(messageCreateRequest);

    MockMultipartFile messageCreateRequestPart = new MockMultipartFile(
        "messageCreateRequest",
        "messageCreateRequest.json",
        MediaType.APPLICATION_JSON_VALUE,
        messageCreateRequestJson.getBytes()
    );

    when(messageService.create(any(MessageCreateRequest.class), anyList())).thenReturn(messageDto);

    mockMvc.perform(multipart("/api/messages")
            .file(messageCreateRequestPart)
            .file("messageCreateRequest",
                messageCreateRequestJson.getBytes())
            .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(messageDto.id().toString()))
        .andExpect(jsonPath("$.content").value("Message content"));
  }


  @Test
  void updateMessageSuccess() throws Exception {
    MessageUpdateRequest messageUpdateRequest = new MessageUpdateRequest("Updated content");
    String requestJson = new ObjectMapper().writeValueAsString(messageUpdateRequest);

    messageDto = new MessageDto(messageId, Instant.now(), Instant.now(), "Updated content",
        channelId, new UserDto(authorId, "testUser", "test@example.com", null, true),
        null);
    when(messageService.update(eq(messageId), any(MessageUpdateRequest.class))).thenReturn(
        messageDto);

    mockMvc.perform(patch("/api/messages/{messageId}", messageId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.content").value("Updated content"));
  }

  @Test
  void deleteMessageSuccess() throws Exception {
    // Perform the DELETE request
    mockMvc.perform(delete("/api/messages/{messageId}", messageId))
        .andExpect(status().isNoContent());  // Expecting HTTP 204 No Content

    // Verify that the service method was called once
    verify(messageService, times(1)).delete(eq(messageId));
  }

  @Test
  void findMessagesByChannelIdSuccess() throws Exception {

    List<MessageDto> messageDtos = Collections.singletonList(messageDto);
    PageResponse<MessageDto> pageResponse = new PageResponse<>(messageDtos, 1, 1, true, 10L);

    when(messageService.findAllByChannelId(eq(channelId), any(), any())).thenReturn(pageResponse);

    mockMvc.perform(get("/api/messages")
            .param("channelId", channelId.toString())
            .param("cursor",
                Instant.now().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(
            messageDto.id().toString()))
        .andExpect(jsonPath("$.content[0].content").value(
            "Message content"));
  }
}
