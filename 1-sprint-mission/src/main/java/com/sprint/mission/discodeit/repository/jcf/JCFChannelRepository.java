package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public class JCFChannelRepository {
//    private static final JCFChannelRepository INSTANCE = new JCFChannelRepository();
//    private JCFChannelRepository(){}

    List<Channel> channels = new ArrayList<>();
    JCFReadStatusRepository readStatusRepository;


//    public static JCFChannelRepository getInstance() {
//        return INSTANCE;
//    }

    private Channel createPrivateChannel(String channelName, UUID userId , String userName){
        Channel channel = new Channel();
        channel.setUpdatedAt(Instant.now());
        channel.setName(channelName);
        channel.setPrivateUsers(userId);
        channel.setCheckPrivateChannel(true);
        channels.add(channel);
        readStatusRepository.createStatus(channel.getChannelId(),channel.getUsers().keySet().stream().toList(),userId);

        return channel;
    }

    public Channel createChannel(ChannelDto channelDto){
        if(channelDto.isChannelPrivate()) return createPrivateChannel(channelDto.getChannelName(), channelDto.getUserId(), channelDto.getUserName());

        Channel channel = new Channel();
        channel.setUpdatedAt(Instant.now());
        channel.setName(channelDto.getChannelName());
        channel.setUsers(channelDto.getUserId(),channelDto.getUserName());
        channel.setCheckPrivateChannel(false);
        channels.add(channel);

        return channel;
    }

    public void joinChannel(ChannelDto channelDto){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelDto.getChannelId())){
                if(!channel.isCheckPrivateChannel()) channel.setUsers(channelDto.getUserId(), channelDto.getUserName());
                else {
                    channel.setPrivateUsers(channelDto.getUserId());
                    readStatusRepository.addUser(channelDto.getChannelId(),channelDto.getUserId());
                }
            }
        }
    }

    public void deleteChannel(UUID deleteChannelId){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(deleteChannelId)){
                channels.remove(channel);
                if(channel.isCheckPrivateChannel()){
                    readStatusRepository.deleteStatus(deleteChannelId);
                }
                break;
            }
        }
    }

    public void checkJoinChannel(UUID checkingUserId, String newName){
        List<Channel> joinedChannel = new ArrayList<>();
        for(Channel channel : channels){
            if(channel.getUsers().containsKey(checkingUserId)){
                joinedChannel.add(channel);
            }
        }

        modifyChannelUser(joinedChannel, checkingUserId, newName);
    }

    private void modifyChannelUser(List<Channel> joinedChannel, UUID checkUserId, String newName) {
        for(Channel channel : channels){
            if(joinedChannel.contains(channel)){
                channel.getUsers().put(checkUserId,newName);
            }
        }
    }

    public boolean getChannelId(UUID id){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public boolean checkChannelContains(UUID channelId){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                return true;
            }
        }

        return false;
    }

    public boolean modifyChannel(UUID channelId, String name) {
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                if(channel.isCheckPrivateChannel()) return false;
                channel.setName(name);
                channel.setUpdatedAt(Instant.now());
                return true;
            }
        }
        return false;
    }

    public Collection<String> joinUserList(UUID channelId){
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                return channel.getUsers().values();
            }
        }

        return new ArrayList<>();
    }

    public void deleteUser(UUID id) {
        for(Channel channel : channels){
            channel.getUsers().remove(id);
            if(channel.isCheckPrivateChannel()){
                readStatusRepository.deleteUser(id);
            }
        }
    }

    public List<Channel> findAllByUserId(ChannelDto channelDto){
        List<Channel> list = new ArrayList<>();

        for(Channel channel : channels){
            if(!channel.isCheckPrivateChannel()) list.add(channel);
            else{
                if(readStatusRepository.checkUser(channel.getChannelId(),channelDto.getUserId())){
                    list.add(channel);
                }
            }
        }

        return list;
    }
}
