package com309.springboot.isumarketplace.Model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mail")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column
    private String recipientEmail;
    @NotNull
    @Lob
    private String content;
    @NotNull
    private String subject;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dateSent = new Date();

    public Mail(){

    }

    public Mail(String recipientEmail, String content, String subject){
        this.recipientEmail = recipientEmail;
        this.content = content;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getContent() {
        return content;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
