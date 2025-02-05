package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.file.interfacepac.FileUserStatusRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileUserStatusRepository implements FileUserStatusRepositoryInterface {
    @Override
    public void setUserStatus(UUID userId, Instant createdAt, Instant updatedAt) throws IOException, ClassNotFoundException {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(userId);
        userStatus.setCreatedAt(createdAt);
        userStatus.setUpdatedAt(updatedAt);
        userStatus.setUserOnOff(true);

        saveStatus(userStatus);
    }

    @Override
    public void update(UUID id,Instant updatedAt) throws IOException, ClassNotFoundException {
        List<UserStatus> userStatuses = getUserStatusListFromFile();
        for(UserStatus userStatus : userStatuses){
            if(userStatus.getId().equals(id)){
                userStatus.setUpdatedAt(updatedAt);
                modifyUserStatusOfFile(userStatuses);
                return;
            }
        }
    }

    @Override
    public void checkUserOnline(UUID userId) throws IOException, ClassNotFoundException {
        List<UserStatus> userStatuses = getUserStatusListFromFile();
        for(UserStatus userStatus : userStatuses){
            if(userStatus.getId().equals(userId)){
                if(userStatus.getUpdatedAt().plus(5, ChronoUnit.MINUTES).compareTo(Instant.now()) > 0){
                    userStatus.setUserOnOff(false);
                    modifyUserStatusOfFile(userStatuses);
                }
                return;
            }
        }

    }

    private void saveStatus(UserStatus readStatus) throws IOException, ClassNotFoundException {
        List<UserStatus> userStatuses = getUserStatusListFromFile();
        userStatuses.add(readStatus);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("userStatus.ser"));
            out.writeObject(userStatuses);
            out.close();
        }
        catch (IOException ignored) {}
    }

    private List<UserStatus> getUserStatusListFromFile() throws IOException, ClassNotFoundException {
        List<UserStatus> userStatuses = new ArrayList<>();

        File file = new File("UserStatus.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                userStatuses = (List<UserStatus>) in.readObject();
                in.close();
            }
        }

        return userStatuses;
    }

    private void modifyUserStatusOfFile(List<UserStatus> readStatuses) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("UserStatus.ser"))) {
            out.writeObject(readStatuses);
            out.close();
        }
    }
}
