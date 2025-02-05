package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Message implements Serializable {
    private UUID messageId = UUID.randomUUID();
    private String text;
    private Instant updatedAt = Instant.now();
    private UUID userId;
    private String userName;
    private UUID channelId;
    private String channelName;
    private int contents;



//    public UUID getMessageId() {
//        return messageId;
//    }
//
//    public void setMessageId(UUID uuid) {
//        messageId = uuid;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
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
//    public UUID getUserId() {
//        return userId;
//    }
//
//    public void setUserId(UUID userId) {
//        this.userId = userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public UUID getChannelId() {
//        return channelId;
//    }
//
//    public void setChannelId(UUID channelId) {
//        this.channelId = channelId;
//    }
//
//    public String getChannelName() {
//        return channelName;
//    }
//
//    public void setChannelName(String channelName) {
//        this.channelName = channelName;
//    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");
    @Override
    public String toString() {
        return "Message\n" +
                "messageId = " + messageId + '\n' +
                "text = " + text + '\n' +
                "channel = " + channelName + "\n" +
                "name = " + userName + '\n' +
                "updatedAt = " + format.format(updatedAt);
    }
}
