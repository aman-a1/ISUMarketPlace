package com309.springboot.isumarketplace.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com309.springboot.isumarketplace.Controller.MessageController;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_conversation")
public class UserConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @OneToMany(mappedBy = "userConversation")
    List<Message> messageContent;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    User conversationWith;
    String lastMessage;

    static class SortByDate implements Comparator<Message> {
        @Override
        public int compare(Message message1, Message message2){
            return message1.getDateSent().compareTo(message2.getDateSent());
        }
    }

    public UserConversation(){

    }

    public UserConversation(User conversationWith, List<Message> messageContent){
        this.conversationWith = conversationWith;

        this.messageContent = messageContent;
//        this.messageContent = new HashSet<>();
//        for(Message message: messageContent){
//            this.messageContent.add(message);
//        }

        this.lastMessage = messageContent.get(messageContent.size()-1).getContent();
    }

//    public UserConversation(User conversationWith, Set<Message> messageSet){
//        this.conversationWith =conversationWith;
//        this.messageContent = messageSet;
//        this.lastMessage = this.getLastMessageFromSet(this.messageContent);
//    }

    public Integer getId() {
        return id;
    }

    public List<Message> getMessageContent() {
        return messageContent;
    }

    public User getConversationWith() {
        return conversationWith;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageFromSet(Set<Message> setOfMessages){
        List<Message> messageList = new ArrayList<>();

        for(Message message: setOfMessages){
            messageList.add(message);
        }

        // Sort Messages by Date
        Collections.sort(messageList, new UserConversation.SortByDate());

        return messageList.get(messageContent.size() - 1).getContent();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMessageContent(List<Message> messageContent) {
        this.messageContent = messageContent;
    }

    public void setConversationWith(User conversationWith) {
        this.conversationWith = conversationWith;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
