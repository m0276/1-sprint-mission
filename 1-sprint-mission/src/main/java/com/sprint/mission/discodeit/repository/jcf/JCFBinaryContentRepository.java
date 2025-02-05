package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.file.interfacepac.FileBinaryContentRepositoryInterface;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class JCFBinaryContentRepository{
    List<BinaryContent> contents = new ArrayList<>();

    public void saveUserContent(UUID id){
        BinaryContent binaryContent = new BinaryContent();
        binaryContent.setUserId(id);
        binaryContent.setCreatedAt(Instant.now());
        binaryContent.setMessageId(null);

        contents.add(binaryContent);
    }

    public void saveMessageContent(UUID id){
        BinaryContent binaryContent = new BinaryContent();
        binaryContent.setMessageId(id);
        binaryContent.setCreatedAt(Instant.now());
        binaryContent.setUserId(null);

        contents.add(binaryContent);
    }

    public void deleteUser(UUID id) {
        contents.removeIf(content -> content.getUserId().equals(id));
    }
}
