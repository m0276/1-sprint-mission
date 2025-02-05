package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


@Getter
@Setter
public class Channel implements Serializable {
    private UUID channelId = UUID.randomUUID();
    private String name;
    private final Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private final Map<UUID, String> users = new HashMap<>();
    private List<UUID> privateUsers = new ArrayList<>();
    private boolean checkPrivateChannel;

//    public UUID getChannelId() {
//        return channelId;
//    }
//
//    public void setChannelId(UUID id){ this.channelId = id;}
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public long getCreatedAt() {
//        return createdAt;
//    }
//
//    public long getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(long updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public Map<UUID, String> getUsers() {
//        return users;
//    }

    public void setUsers(UUID userId, String name){
        users.put(userId,name);
    }

    public void setPrivateUsers(UUID userId){
        privateUsers.add(userId);
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");
    @Override
    public String toString() {
        return "Channel\n" +
                "channel id = " + channelId + '\n' +
                "name = " + name + '\n' +
                "createdAt = " + format.format(createdAt) + '\n' +
                "updatedAt = " + format.format(updatedAt) +'\n' +
                "users = " + users.values();
    }

}
