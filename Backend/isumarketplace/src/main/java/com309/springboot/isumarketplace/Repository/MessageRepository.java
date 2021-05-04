package com309.springboot.isumarketplace.Repository;

import com309.springboot.isumarketplace.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderUsernameAndReceiverUsername(String senderUsername, String receiverUsername);
    List<Message> findBySenderUsername(String senderUsername);
    List<Message> findByReceiverUsername(String receiverUsername);
}
