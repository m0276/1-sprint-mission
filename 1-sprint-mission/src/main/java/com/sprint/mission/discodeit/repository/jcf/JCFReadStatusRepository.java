package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class JCFReadStatusRepository {
    private List<ReadStatus> statuses = new ArrayList<>();

    public void createStatus(UUID channelId, List<UUID> userList, UUID readUserId){
        ReadStatus readStatus = new ReadStatus();
        readStatus.setChannelId(channelId);
        readStatus.setUsers(userList, readUserId);
        readStatus.setCreatedAt(Instant.now());

        statuses.add(readStatus);
    }

    public void addUser(UUID channelId, UUID userId) {
        for(ReadStatus readStatus : statuses){
            if(readStatus.getChannelId().equals(channelId)){
                readStatus.getCheck().put(userId,true);
            }
        }
    }

    public void deleteStatus(UUID deleteChannelId) {
        statuses.removeIf(status -> status.getChannelId().equals(deleteChannelId));
    }

    public void deleteUser(UUID id) {
        for(ReadStatus readStatus : statuses){
            readStatus.getCheck().remove(id);
        }
    }

    public boolean checkUser(UUID channelId ,UUID id) {
        for(ReadStatus readStatus : statuses){
            if(readStatus.getChannelId().equals(channelId) && readStatus.getCheck().containsKey(id)) return true;
        }

        return false;
    }
}
