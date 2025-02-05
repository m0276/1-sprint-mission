package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
public class User implements Serializable {
    private UUID id = UUID.randomUUID();
    private String name;
    private final Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private String password;
    private String email;

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id){
//        this.id = id;
//    }
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
//
//    public long getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(long updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

    @Override
    public String toString() {
        return "User\n" +
                "id = " + id + '\n' +
                "name = " + name + '\n' +
                "email = " + email + '\n' +
                "createdAt = " + format.format(createdAt) + '\n' +
                "updatedAt = " + format.format(updatedAt);
    }

}
