package com.sprint.mission.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.service.EntityService;

import java.text.SimpleDateFormat;
import java.util.*;

public class JCFEntityServiceUser implements EntityService{
    private static final JCFEntityServiceUser INSTANCE = new JCFEntityServiceUser();
    private JCFEntityServiceUser() {}

    public static JCFEntityServiceUser getInstance() {
        return INSTANCE;
    }

    private final List<User> users = new ArrayList<>();

    public UUID createUser(String nickname) {
        User user = new User();
        user.setName(nickname);
        user.setUpdatedAt(System.currentTimeMillis());
        user.setCreatedAt(System.currentTimeMillis());
        user.setId(UUID.randomUUID());
        users.add(user);
        return user.getId();
    }

    public void update(UUID id, String newNickname) {
        for (User user : users) {
            if(user.getId().equals(id)){
                user.setName(newNickname);
                user.setUpdatedAt(System.currentTimeMillis());
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void showInfo(UUID id){
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

        boolean checkActiveUser = true;
        User wantShowUser = null;
        for (User user : users) {
            if(user.getId().equals(id)){
                wantShowUser = user;
                checkActiveUser = false;
                break;
            }
        }
        if(checkActiveUser) System.out.println("존재하지 않는 유저입니다");
        else{
            System.out.println("name = " + wantShowUser.getName());
            System.out.println("create date = " + format.format(wantShowUser.getCreatedAt()));
            System.out.println("last update date = " + format.format(wantShowUser.getUpdatedAt()));
        }

    }

    public String getUserName(UUID id){
        for(User user : users){
            if(user.getId().equals(id)){
                return user.getName();
            }
        }

        return "Undefined";
    }
}
