package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.service.basic.BasicReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ReadStatusController {
    private final BasicReadStatusService readStatusService;

    @RequestMapping(value = "/message/status", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody MessageDto messageDto){
        try{
            readStatusService.saveMessage(messageDto);
        } catch (IOException | ClassNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/message/status", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody MessageDto messageDto) {
        try {
            System.out.println(readStatusService.findMessageStatus(messageDto));
        } catch (IOException | ClassNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/message/status/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Void> getUserStatus(@PathVariable UUID userId) {
        try {
            System.out.println(readStatusService.findAllByUserId(userId));
        } catch (IOException | ClassNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
