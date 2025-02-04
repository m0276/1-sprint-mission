package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;

import java.util.*;

public class JCFUserRepository {
    private static final JCFUserRepository INSTANCE = new JCFUserRepository();
    private JCFUserRepository() {}

    public static JCFUserRepository getInstance() {
        return INSTANCE;
    }

    private final List<User> users = new ArrayList<>();

    public User createUser(String nickname) {
        User user = new User();
        user.setName(nickname);
        users.add(user);
        return user;
    }

    public void update(UUID id, String newNickname) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(newNickname);
                user.setUpdatedAt(System.currentTimeMillis());
            }
        }
    }

    public boolean checkUserId(UUID id){
        for(User user : users){
            if(user.getId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public void deleteUser(UUID id) {
        for(User user : users){
            if(user.getId().equals(id)){
                users.remove(user);
                break;
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
