package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.*;
import java.util.UUID;

@Getter
@Setter
public class ReadStatus {
    private UUID statusId;
    private UUID channelId;
    private Map<UUID,Boolean> check;
    private Instant createdAt;
    private Instant updatedAt;

    public void setFirstStatus(UUID statusId, Instant createdAt, Instant updatedAt, List<UUID> list, UUID userId) {
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        for(UUID uid : list){
            if(uid.equals(userId)){
                check.put(uid,true);
            }
            else{
                check.put(uid,false);
            }
        }
    }

    public void setUsers(List<UUID> list, UUID userId){
        for(UUID uid : list){
            if(uid.equals(userId)){
                check.put(uid,true);
            }
            else{
                check.put(uid,false);
            }
        }
    }

}
