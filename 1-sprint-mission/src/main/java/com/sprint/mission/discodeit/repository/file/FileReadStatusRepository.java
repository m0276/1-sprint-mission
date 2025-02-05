package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.file.interfacepac.FileReadStatusRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileReadStatusRepository {

    public void createStatus(UUID channelId, List<UUID> userList, UUID readUserId) throws IOException, ClassNotFoundException {
        ReadStatus readStatus = new ReadStatus();
        readStatus.setChannelId(channelId);
        readStatus.setUsers(userList, readUserId);
        readStatus.setCreatedAt(Instant.now());

        saveStatus(readStatus);
    }

    public void addUser(UUID channelId, UUID userId) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        for(ReadStatus readStatus : statuses){
            if(readStatus.getChannelId().equals(channelId)){
                readStatus.getCheck().put(userId,true);
            }
        }
        modifyReadStatusOfFile(statuses);
    }

    public void deleteStatus(UUID deleteChannelId) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        statuses.removeIf(status -> status.getChannelId().equals(deleteChannelId));
        modifyReadStatusOfFile(statuses);

    }

    public void deleteUser(UUID id) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        for(ReadStatus readStatus : statuses){
            readStatus.getCheck().remove(id);
        }
        modifyReadStatusOfFile(statuses);
    }

    public boolean checkUser(UUID channelId ,UUID id) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        for(ReadStatus readStatus : statuses){
            if(readStatus.getChannelId().equals(channelId) && readStatus.getCheck().containsKey(id)) return true;
        }

        return false;
    }

    public void findChannel(UUID channelId) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        for(ReadStatus readStatus : statuses){
            if(readStatus.getChannelId().equals(channelId)){
                System.out.println(readStatus.getCheck().keySet());
            }
        }
    }

    public ReadStatus findStatus(UUID channelId) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        for(ReadStatus readStatus : statuses){
            if(readStatus.getChannelId().equals(channelId)) return readStatus;
        }


        return null;
    }

    public List<UUID> findByUserId(UUID userId) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        List<UUID> list = new ArrayList<>();

        for(ReadStatus readStatus : statuses){
            if(readStatus.getCheck().containsKey(userId)) list.add(readStatus.getChannelId());
        }

        return list;
    }

    private void saveStatus(ReadStatus readStatus) throws IOException, ClassNotFoundException {
        List<ReadStatus> userStatuses = getReadStatusListFromFile();
        userStatuses.add(readStatus);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ReadStatus.ser"));
            out.writeObject(userStatuses);
            out.close();
        }
        catch (IOException ignored) {}
    }

    private List<ReadStatus> getReadStatusListFromFile() throws IOException, ClassNotFoundException {
        List<ReadStatus> readStatuses = new ArrayList<>();


        File file = new File("ReadStatus.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                readStatuses = (List<ReadStatus>) in.readObject();
                in.close();
            }
        }

        return readStatuses;
    }

    private void modifyReadStatusOfFile(List<ReadStatus> readStatuses) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ReadStatus.ser"))) {
            out.writeObject(readStatuses);
            out.close();
        }
    }

    private boolean checkReadStatusId(UUID id) throws IOException, ClassNotFoundException {
        List<ReadStatus> statuses = getReadStatusListFromFile();
        for(ReadStatus status : statuses){
            if(status.getStatusId().equals(id)){
                return true;
            }
        }

        return false;
    }
}
