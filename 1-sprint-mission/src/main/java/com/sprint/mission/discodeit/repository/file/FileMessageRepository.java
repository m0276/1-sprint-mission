package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileMessageRepository implements FileMessageRepositoryInterface{
    private static final FileMessageRepository INSTANCE = new FileMessageRepository();
    private FileMessageRepository(){}
    public static FileMessageRepository getInstance() {
        return INSTANCE;
    }

    public Message createMessage(String text, UUID userId, String userName ,UUID channelId,String channelName) throws IOException, ClassNotFoundException {
        Message message = new Message();
        message.setText(text);
        message.setUserId(userId);
        message.setUserName(userName);
        message.setChannelId(channelId);
        message.setChannelName(channelName);

        setMessageListToFile(message);
        return message;
    }

    public void modifyMessage(UUID Userid, UUID messageId ,String text) throws IOException, ClassNotFoundException {
        List<Message> messagesList = getMessageFromFile();

        if (messagesList != null) {
            for (Message message : messagesList) {
                if (message.getUserId().equals(Userid) && message.getMessageId().equals(messageId)) {
                    message.setText(text);
                    message.setUpdatedAt(System.currentTimeMillis());
                    ModifyListToFile(messagesList);
                    break;
                }
            }
        }
    }

    public List<Message> getMessagesOfChannel(UUID channelId) throws IOException, ClassNotFoundException {
        List<Message> messagesList = getMessageFromFile();
        return messagesList.stream()
                .filter(x -> x.getChannelId().equals(channelId))
                .toList();
    }

    public void deleteMessage(UUID messageId) throws IOException, ClassNotFoundException {
        List<Message> messagesList = getMessageFromFile();

        if(messagesList != null){
            for(Message message : messagesList){
                if(message.getMessageId().equals(messageId)){
                    messagesList.remove(message);
                    break;
                }
            }
        }
        ModifyListToFile(messagesList);
    }

    public boolean checkMessageContains(UUID id) throws IOException, ClassNotFoundException {
        List<Message> messagesList = getMessageFromFile();

        for(Message message : messagesList){
            if(message.getMessageId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public void modifyUserName(UUID userId, String newUserName) throws IOException, ClassNotFoundException {
        List<Message> messagesList = getMessageFromFile();
        if(messagesList.isEmpty()){
            return;
        }

        for(Message message : messagesList){
            if(message.getUserId().equals(userId)){
                message.setUserName(newUserName);
            }
        }

        ModifyListToFile(messagesList);
    }

    public void modifyChannelName(UUID channelId,String newChannelId) throws IOException, ClassNotFoundException {
        List<Message> messagesList = getMessageFromFile();

        if(messagesList.isEmpty()){
            return;
        }

        for(Message message : messagesList){
            if(message.getChannelId().equals(channelId)){
                message.setChannelName(newChannelId);
            }
        }

        ModifyListToFile(messagesList);
    }

    public void deletedChannelMessage(UUID channelId) throws IOException, ClassNotFoundException {
        List<Message> messagesList = getMessageFromFile();
        if(messagesList.isEmpty()){
            return;
        }

        messagesList.removeIf(message -> message.getChannelId().equals(channelId));

        ModifyListToFile(messagesList);
    }

    private void setMessageListToFile(Message message) throws IOException, ClassNotFoundException {
        List<Message> messages = getMessageFromFile();
        messages.add(message);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Message.ser"));
            out.writeObject(messages);
        }
        catch (IOException ignored) {}
    }

    private void ModifyListToFile(List<Message> messages) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Message.ser"))) {
                out.writeObject(messages);
        }
    }

    private List<Message> getMessageFromFile() throws IOException, ClassNotFoundException {
        List<Message> messages = new ArrayList<>();


        File file = new File("Message.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                messages = (List<Message>) in.readObject();
            }
        }

        return messages;
    }


    public Message getMessage(Message message) throws IOException, ClassNotFoundException {
        List<Message> messageList = getMessageFromFile();

        for(Message message1 : messageList){
            if(message1.getMessageId().equals(message.getMessageId())){
                return message1;
            }
        }

        return null;
    }

    public List<Message> getMessages() throws IOException, ClassNotFoundException {
        return getMessageFromFile();
    }
}
