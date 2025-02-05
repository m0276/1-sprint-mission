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
public class FileReadStatusRepository implements FileReadStatusRepositoryInterface {

    @Override
    public void setStatus(Instant createdAt, Instant updatedAt, List<UUID> list, UUID userId) throws IOException, ClassNotFoundException {
        ReadStatus readStatus = new ReadStatus();
        UUID statusId = UUID.randomUUID();
        if(checkReadStatusId(statusId)){
            while(!checkReadStatusId(statusId)){
                statusId = UUID.randomUUID();
            }
        }

        readStatus.setFirstStatus(statusId,createdAt,updatedAt,list, userId);
        saveStatus(readStatus);
    }

    @Override
    public void update(UUID id ,Instant updatedAt) throws IOException, ClassNotFoundException {
        List<ReadStatus> readStatuses = getReadStatusListFromFile();

        for(ReadStatus readStatus : readStatuses){
            if(readStatus.getStatusId().equals(id)){
                readStatus.setUpdatedAt(updatedAt);
                modifyReadStatusOfFile(readStatuses);
                return;
            }
        }

    }

    @Override
    public List<UUID> setUserForRead(List<UUID> list){
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
