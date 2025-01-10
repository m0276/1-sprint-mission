package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.*;

public class Channel{
    private UUID channelId;
    private String name;
    private long createdAt;
    private long updatedAt;
    private final List<UUID> users = new ArrayList<>();
    private final List<String> userNames = new ArrayList<>();

    public UUID getChannelId() {
        return channelId;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUsers(UUID userId) {
        this.users.add(userId);
    }

    public List<UUID> getUsers() {
        return users;
    }

    public List<String> getUserName() {
        return userNames;
    }

    public void setUserName(String userName){
        userNames.add(userName);
    }

    @Override
    public String toString() {
        return users.toString();
    }

}
