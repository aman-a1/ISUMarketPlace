package com309.springboot.isumarketplace.WebSocket;

import com309.springboot.isumarketplace.Exceptions.OnSuccessException;
import com309.springboot.isumarketplace.Exceptions.ResourceNotFoundException;
import com309.springboot.isumarketplace.Model.Message;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.MessageRepository;
import com309.springboot.isumarketplace.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Controller
@ServerEndpoint("/websocket/{username}/{receiverUsername}")
public class WebSocketServer {
    private static MessageRepository messageRepository;
    private static UserRepository userRepository;

    //Grabs the MessageRepository singleton from the Spring Application Context
    @Autowired
    public void setMessageRepository(MessageRepository messageRepo){
        messageRepository = messageRepo;
    }

    @Autowired
    public void setUserRepository(UserRepository usrRepository){ userRepository = usrRepository; }

    // Store all socket session and their corresponding username
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    static class SortByDate implements Comparator<Message> {
        @Override
        public int compare(Message message1, Message message2){
            return message1.getDateSent().compareTo(message2.getDateSent());
        }
    }

    @OnOpen
    public void onOpen( Session session, @PathParam("username") String username,
                        @PathParam("receiverUsername") String receiverUsername)
            throws IOException, ResourceNotFoundException, OnSuccessException {

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        //String chatHistory = getChatHistory(username, receiverUsername);

        //usernameSessionMap.get(username).getBasicRemote().sendText(chatHistory);

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        // Handle new messages
        logger.info("Entered into Message: Got Message:"+message);
        String senderUsername = sessionUsernameMap.get(session);
        //System.out.println();

        if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
        {
            // Get recipient username
            String receiverUsername = message.split(" ")[0].substring(1); // don't do this in your code!
            // Remove @username
            String getMessage = getMessageContent(message);

            if(usernameSessionMap.get(receiverUsername) == null){
                sendMessageToPArticularUser(senderUsername, senderUsername + ": " + getMessage);

                Message newMessage = new Message(senderUsername, receiverUsername, getMessage);
                messageRepository.save(newMessage);
            }else{
                sendMessageToPArticularUser(receiverUsername, senderUsername + ": " + getMessage);
                sendMessageToPArticularUser(senderUsername, senderUsername + ": " + getMessage);

                Message newMessage = new Message(senderUsername, receiverUsername, getMessage);
                messageRepository.save(newMessage);
            }

        }
        else // Message to whole chat
        {
            broadcast(senderUsername + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        //logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        //String message= username + " disconnected";
        //broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private void sendMessageToPArticularUser(String username, String message)
    {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private void broadcast(String message)
    {
        sessionUsernameMap.forEach((session, username)->{
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }

    // Gets the Chat history from the repository
    private String getChatHistory(String senderUsername, String receiverUsername) {
        //List<Message> messages = messageRepository.findAll();
        List<Message> messageHistory = messageRepository.findBySenderUsernameAndReceiverUsername(senderUsername, receiverUsername);
        List<Message> messageHistory1 = messageRepository.findBySenderUsernameAndReceiverUsername(receiverUsername, senderUsername);

        List<Message> allHistory = new ArrayList<Message>();
        allHistory.addAll(messageHistory);
        allHistory.addAll(messageHistory1);

        // convert the list to a string
        StringBuilder sb = new StringBuilder();

        if(allHistory != null && allHistory.size() != 0) {
            Collections.sort(allHistory, new SortByDate());

            allHistory.forEach(message -> {
                sb.append(message.getSenderUsername() + ": " + message.getContent() + "\n");
            });
        }

        return sb.toString();
    }

    private String getMessageContent(String message){

        int msgIndex = 0;

        for(int i = 0; i < message.length(); i++){
            if(message.charAt(i) == ' '){
                msgIndex = i;
                break;
            }
        }

        String returnMessage = message.substring(msgIndex+1, message.length());

        return returnMessage;
    }

}
