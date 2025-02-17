package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ChannelController {
    private final BasicChannelService channelService;

    @RequestMapping(value = "/channel/{channelName}",method = RequestMethod.GET)
    public ResponseEntity<Void> showInfo(@PathVariable String channelName) {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelName(channelName);

        try {
            channelService.showInfoChannel(channelDto);
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("존재하지 않는 채널입니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @RequestMapping(value = "/channel/user/{userId}",method = RequestMethod.GET)
    public ResponseEntity<Void> showInfoOfUser(@PathVariable UUID userId) {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setUserId(userId);

        try {
            channelService.showInfoChannelOfChannel(channelDto);
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @RequestMapping(value = "/channel", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody ChannelDto channelDto) {
        try {
            channelService.saveChannel(channelDto);

        } catch (IOException | ClassNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/channel", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody ChannelDto channelDto) {
        try {
            channelService.modifyChannel(channelDto);
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @RequestMapping(value = "/channel/{channelId}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable UUID channelId) {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setChannelId(channelId);

        try {
            channelService.deleteChannel(channelDto);
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
