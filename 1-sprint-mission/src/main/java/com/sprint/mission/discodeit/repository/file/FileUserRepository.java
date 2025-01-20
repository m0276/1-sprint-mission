package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUserRepository implements FileUserRepositoryInterface {
    private static final FileUserRepository INSTANCE = new FileUserRepository();
    private FileUserRepository() {}

    public static FileUserRepository getInstance() {
        return INSTANCE;
    }

    public User createUser(String nickname) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setName(nickname);
        if(checkUserId(user.getId())){
            while(checkUserId(user.getId())){
                user.setId(UUID.randomUUID());
            }
        }
        saveUser(user);
        return user;
    }

    public void modifyUser(UUID id, String newNickname) throws IOException, ClassNotFoundException {
        List<User> users = getUserListFromFile();
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(newNickname);
                user.setUpdatedAt(System.currentTimeMillis());
                break;
            }
        }

        modifyUserOfFile(users);
    }

    public boolean checkUserId(UUID id) throws IOException, ClassNotFoundException {
        List<User> users = getUserListFromFile();
        for(User user : users){
            if(user.getId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public void deleteUser(UUID id) throws IOException, ClassNotFoundException {
        List<User> users = getUserListFromFile();
        for(User user : users){
            if(user.getId().equals(id)){
                users.remove(user);
                break;
            }
        }
        modifyUserOfFile(users);
    }

    private void saveUser(User user) throws IOException, ClassNotFoundException {
        List<User> users = getUserListFromFile();
        users.add(user);


        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("User.ser"));
            out.writeObject(users);
            out.close();
        }
        catch (IOException ignored) {}
    }


    private List<User> getUserListFromFile() throws IOException, ClassNotFoundException {
        List<User> users = new ArrayList<>();


        File file = new File("User.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                users = (List<User>) in.readObject();
            }
        }

        return users;
    }

    public User getUser(User user) throws IOException, ClassNotFoundException {
        List<User> user1 = getUserListFromFile();
        for(User user2 : user1){
            if(user2.getId().equals(user.getId())){
                return user2;
            }
        }

        System.out.println("해당하는 유저가 없습니다.");
        return null;
    }

    private void modifyUserOfFile(List<User> users) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("User.ser"))) {
                out.writeObject(users);
        }
    }
}
