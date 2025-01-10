package com.sprint.mission.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.service.EntityService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class JCFEntityServiceChannel implements EntityService{
    private static final JCFEntityServiceChannel INSTANCE = new JCFEntityServiceChannel();
    private JCFEntityServiceChannel(){}

    List<Channel> channels = new ArrayList<>();

    public static JCFEntityServiceChannel getInstance() {
        return INSTANCE;
    }

    public UUID createChannel(String channelName , UUID userId, String userName){
        Channel channel = new Channel();
        channel.setCreatedAt(System.currentTimeMillis());
        channel.setUpdatedAt(System.currentTimeMillis());
        channel.setName(channelName);
        channel.setUsers(userId);
        channel.setChannelId(UUID.randomUUID());
        channel.setUserName(userName);
        channels.add(channel);
        return channel.getChannelId();
    }

    public void joinChannel(UUID channelId, UUID userId, String userName){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                channel.setUsers(userId);
                channel.setUserName(userName);
            }
        }
    }

    public void deleteChannel(UUID deleteChannelId){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(deleteChannelId)){
                channels.remove(channel);
                break;
            }
        }
    }

    public void update(UUID channelId, String newName){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                channel.setName(newName);
                break;
            }
        }
    }

    public void checkJoinChannel(UUID checkingUserId){
        List<String> channelName = new ArrayList<>();
        for(Channel channel : channels){
            if(channel.getUsers().contains(checkingUserId)){
                channelName.add(channel.getName());
            }
        }

        System.out.println("님이 참여 중인 채널은 " + channelName + " 입니다");
    }

    @Override
    public void showInfo(UUID channelId){
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

        boolean checkActiveChannel = true;
        Channel wantShowChannel = null;
        for (Channel channel : channels) {
            if(channel.getChannelId().equals(channelId)){
                wantShowChannel = channel;
                checkActiveChannel = false;
                break;
            }
        }
        if(checkActiveChannel) System.out.println("존재하지 않는 채널입니다");
        else{
            System.out.println("name = " + wantShowChannel.getName());
            System.out.println("create date = " + format.format(wantShowChannel.getCreatedAt()));
            System.out.println("last update date = " + format.format(wantShowChannel.getUpdatedAt()));
            System.out.println(wantShowChannel.getUserName());
        }
    }
}