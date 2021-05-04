package com309.springboot.isumarketplace.Controller;

import com309.springboot.isumarketplace.Exceptions.ResourceNotFoundException;
import com309.springboot.isumarketplace.Model.Message;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Model.UserConversation;
import com309.springboot.isumarketplace.Repository.MessageRepository;
import com309.springboot.isumarketplace.Repository.UserRepository;
import com309.springboot.isumarketplace.WebSocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    static class SortByDate implements Comparator<Message> {
        @Override
        public int compare(Message message1, Message message2){
            return message1.getDateSent().compareTo(message2.getDateSent());
        }
    }

    @GetMapping("/GetAllMessages")
    public List<Message> getAllMessages(){
        List<Message> getAllMessages = messageRepository.findAll();

        return getAllMessages;
    }

    @GetMapping("/Messages/{username}")
    public List<UserConversation> getAllMessagesByUsername(@PathVariable String username) throws ResourceNotFoundException {

        User getUsername = userRepository.findByUserName(username);

        if(getUsername == null){
            throw new ResourceNotFoundException("No username found!");
        }

        List<Message> getSentMessages = messageRepository.findBySenderUsername(username);
        List<Message> getReceivedMessages = messageRepository.findByReceiverUsername(username);
        List<UserConversation> userConversations = new ArrayList<UserConversation>();
        List<String> usernames = new ArrayList<>();

        usernames.addAll(sentMessagesReceiver(getSentMessages));
        usernames.addAll(receivedMessagesSender(getReceivedMessages));

        List<String> usernameNoDuplicate = removeDuplicateUsername(usernames);

        for(String doeUsername: usernameNoDuplicate){
            User otherUser = userRepository.findByUserName(doeUsername);
            otherUser.setUserPassword(null);

            List<Message> messageHistory = messageRepository.findBySenderUsernameAndReceiverUsername(username, doeUsername);
            List<Message> messageHistory1 = messageRepository.findBySenderUsernameAndReceiverUsername(doeUsername, username);

            List<Message> fullConversation = new ArrayList<>();

            fullConversation.addAll(messageHistory);
            fullConversation.addAll(messageHistory1);

            Collections.sort(fullConversation, new MessageController.SortByDate());

            UserConversation userConversation = new UserConversation(otherUser, fullConversation);

            userConversations.add(userConversation);
        }

        return userConversations;
    }

    @GetMapping("/Messages/{username}/{otherUsername}")
    public List<Message> getConversationHistory(@PathVariable String username, @PathVariable String otherUsername)
            throws ResourceNotFoundException {
        User user = userRepository.findByUserName(username);
        User otherUser = userRepository.findByUserName(otherUsername);

        if(user == null || otherUser == null){
            throw new ResourceNotFoundException("User not found!");
        }

        List<Message> messageHistory = messageRepository.findBySenderUsernameAndReceiverUsername(username, otherUsername);
        List<Message> messageHistory1 = messageRepository.findBySenderUsernameAndReceiverUsername(otherUsername, username);

        List<Message> fullConversation = new ArrayList<>();

        fullConversation.addAll(messageHistory);
        fullConversation.addAll(messageHistory1);

        Collections.sort(fullConversation, new MessageController.SortByDate());

        return fullConversation;
    }

    public List<String> sentMessagesReceiver(List<Message> messageList){
        List<String> usernameList = new ArrayList<>();

        for(Message message: messageList){
            if(!usernameList.contains(message.getReceiverUsername())){
                usernameList.add(message.getReceiverUsername());
            }
        }

        return usernameList;
    }

    public List<String> receivedMessagesSender(List<Message> messageList){
        List<String> usernameList = new ArrayList<>();

        for(Message message: messageList){
            if(!usernameList.contains(message.getSenderUsername())){
                usernameList.add(message.getSenderUsername());
            }
        }

        return usernameList;
    }

    public List<String> removeDuplicateUsername(List<String> usernames){
        Set<String> usernameSet = new HashSet<>();

        for(String username: usernames){
            usernameSet.add(username);
        }

        List<String> usernameList = new ArrayList<>();

        for(String username: usernameSet){
            usernameList.add(username);
        }

        return usernameList;
    }
}
