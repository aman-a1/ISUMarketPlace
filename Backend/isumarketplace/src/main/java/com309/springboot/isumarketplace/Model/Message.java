package com309.springboot.isumarketplace.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column
    private String senderUsername;
    @NotNull
    @Column
    private String receiverUsername;
    @NotNull
    @Lob
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_conversation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    UserConversation userConversation;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dateSent = new Date();

    public Message(){}

    public Message(String senderUsername, String receiverUsername, String content){
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getContent() {
        return content;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
