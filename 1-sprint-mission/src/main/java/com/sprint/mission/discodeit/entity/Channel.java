package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Channel implements Serializable {
    private UUID channelId = UUID.randomUUID();
    private String name;
    private final long createdAt = System.currentTimeMillis();
    private long updatedAt = System.currentTimeMillis();
    private final Map<UUID, String> users = new HashMap<>();

    public UUID getChannelId() {
        return channelId;
    }

    public void setChannelId(UUID id){ this.channelId = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<UUID, String> getUsers() {
        return users;
    }

    public void setUsers(UUID userId, String name){
        users.put(userId,name);
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
