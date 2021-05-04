package com309.springboot.isumarketplace.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Notification id", name = "id", value = "Integer")
    Integer id;
    @ApiModelProperty(notes = "Notification message", name = "message", required = true, value = "String")
    @Column
    String message;
    @ApiModelProperty(notes = "Notification date created", name = "dateCreated", required = true, value = "Date")
    @Column
    Date dateCreated;
    @ApiModelProperty(notes = "Notification read status", name = "hasRead", required = true, value = "Boolean")
    @Column
    Boolean hasRead;
    @ApiModelProperty(notes = "User notifications(Many-to-one relationship)", name = "userNotification", required = true, value = "User")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User userNotification;

    public Notification(){};

    public Notification(String message, Date dateCreated, Boolean hasRead){
        this.message = message;
        this.dateCreated = dateCreated;
        this.hasRead = hasRead;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public User getUserNotification() {
        return userNotification;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public void setUserNotification(User userNotification) {
        this.userNotification = userNotification;
    }
}
