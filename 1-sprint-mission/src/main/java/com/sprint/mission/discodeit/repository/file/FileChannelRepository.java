package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.io.*;
import java.util.*;


public class FileChannelRepository implements FileChannelRepositoryInterface {
    private static final FileChannelRepository INSTANCE = new FileChannelRepository();
    private FileChannelRepository(){}

    public static FileChannelRepository getInstance() {
        return INSTANCE;
    }

    public Channel createChannel(String channelName, User user) throws IOException, ClassNotFoundException {
        Channel channel = new Channel();

        if(checkChannelContains(channel.getChannelId())){
            while(checkChannelContains(channel.getChannelId())){
                channel.setChannelId(UUID.randomUUID());
            }
        }

        channel.setName(channelName);
        channel.setUsers(user.getId(),user.getName());

        setChannelListToFile(channel);
        return channel;
    }

    private boolean checkChannelContains(UUID channelId) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                return true;
            }
        }

        return false;
    }

    public void joinChannel(UUID channelId, UUID userId, String userName) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                channel.setUsers(userId,userName);
                break;

            }
        }
        modifyChannelOfFile(channels);
    }

    public void deleteChannel(UUID deleteChannelId) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();
        if(channels.isEmpty()){
            System.out.println("없는 서버입니다");
            return;
        }

        for(Channel channel : channels){
            if(channel.getChannelId().equals(deleteChannelId)){
                channels.remove(channel);
                modifyChannelOfFile(channels);
                return;
            }
        }

        System.out.println("없는 서버입니다");
    }

    public void modifyChannelUserName(UUID checkingUserId, String newName) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();

        for(Channel channel : channels){
            if(channel.getUsers().containsKey(checkingUserId)){
                channel.setUsers(checkingUserId,newName);
            }
        }

        modifyChannelOfFile(channels);
    }

    public boolean getChannelId(UUID id) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();

        for(Channel channel : channels){
            if(channel.getChannelId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public void modifyChannel(UUID channelId, String name) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                channel.setName(name);
                channel.setUpdatedAt(System.currentTimeMillis());
                break;
            }
        }
        modifyChannelOfFile(channels);
    }

    public Collection<String> joinUserList(UUID channelId) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();
        for(Channel channel : channels){
            if(channel.getChannelId().equals(channelId)){
                return channel.getUsers().values();
            }
        }

        return new ArrayList<>();
    }

    public void deleteUser(UUID id) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();

        for(Channel channel : channels){
            channel.getUsers().remove(id);
        }

        modifyChannelOfFile(channels);
    }

    private void setChannelListToFile(Channel channel) throws IOException, ClassNotFoundException {
        List<Channel> channels= getChannelListFromFile();
        channels.add(channel);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Channel.ser"));
            out.writeObject(channels);
            out.close();
        }
        catch (IOException ignored) {}

    }

    private void modifyChannelOfFile(List<Channel> channels) throws IOException {
        try (
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Channel.ser"))) {
            out.writeObject(channels);
        }
    }

    private List<Channel> getChannelListFromFile() throws IOException, ClassNotFoundException {
        List<Channel> channels = new ArrayList<>();

        File file = new File("Channel.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                channels = (List<Channel>) in.readObject();
            }
        }

        return channels;
    }

    public Channel getChannel(Channel channel) throws IOException, ClassNotFoundException {
        List<Channel> channels = getChannelListFromFile();

        for(Channel chan : channels){
            if(channel.getChannelId().equals(chan.getChannelId())){
                return chan;
            }
        }

        return null;
    }

    public List<Channel> getChannels() throws IOException, ClassNotFoundException {
        return getChannelListFromFile();
    }

}
