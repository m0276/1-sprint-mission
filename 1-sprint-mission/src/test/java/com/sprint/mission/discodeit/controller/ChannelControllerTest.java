package com.sprint.mission.discodeit.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicReadStatusService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.basic.BasicUserStatusService;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WebMvcTest(ChannelController.class)
@ActiveProfiles("test")
class ChannelControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BasicChannelService channelService;

  @MockitoBean
  private BasicReadStatusService readStatusService;

  @MockitoBean
  private ChannelRepository repository;

  @MockitoBean
  private ChannelMapper userMapper;

  @Test
  void findChannel() throws Exception {
    UserDto userDto = new UserDto(UUID.randomUUID(), "user", "test@example.com", null, true);
    UUID channelId = UUID.randomUUID();
    // Create a list of ChannelDto to return when the service method is called
    List<ChannelDto> channelDtos = Arrays.asList(
        new ChannelDto(channelId, ChannelType.PUBLIC, "Channel 1", "channel-1", List.of(userDto),
            Instant.now()),

        new ChannelDto(channelId, ChannelType.PRIVATE, "Channel 2", "channel-2", List.of(userDto),
            Instant.now())
    );

    // Mock the service call to return the predefined list of ChannelDto
    when(channelService.findAllByUserId(userDto.id())).thenReturn(channelDtos);

    // Perform the GET request
    mockMvc.perform(get("/api/channels")
            .param("userId", userDto.id().toString())  // Passing the userId as a query parameter
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())  // Expecting a 200 OK response
        .andExpect(
            jsonPath("$.length()").value(2))  // Check that the response body contains 2 channels
        .andExpect(jsonPath("$[0].name").value(
            "Channel 1"))  // Check that the first channel's name is "Channel 1"
        .andExpect(jsonPath("$[1].name").value(
            "Channel 2"));  // Check that the second channel's name is "Channel 2"

  }

  @Test
  void createChannelSuccess() throws Exception {
    UUID channelId = UUID.randomUUID();

    // Prepare the request object (PublicChannelCreateRequest)
    PublicChannelCreateRequest request = new PublicChannelCreateRequest("channel", "1channel");

    // Convert the request object to JSON string
    String requestJson = new ObjectMapper().writeValueAsString(request);

    // Mock the service call to return a valid ChannelDto
    ChannelDto channelDto = new ChannelDto(channelId, ChannelType.PUBLIC, "channel", "1channel",
        null,
        Instant.now());
    when(channelService.create(request)).thenReturn(channelDto);

    // Perform the mock POST request to /api/channels/public with the request body
    mockMvc.perform(post("/api/channels/public")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("channel"))
        .andExpect(jsonPath("$.description").value("1channel"));
  }

}


