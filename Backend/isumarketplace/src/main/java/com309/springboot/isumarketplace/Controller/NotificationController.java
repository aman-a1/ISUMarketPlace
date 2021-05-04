package com309.springboot.isumarketplace.Controller;

import com309.springboot.isumarketplace.Exceptions.ResourceNotFoundException;
import com309.springboot.isumarketplace.Model.Notification;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.NotificationRepository;
import com309.springboot.isumarketplace.Repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/Notifications")
    List<Notification> getAllNotifications(){
        List<Notification> getAllNotifications = notificationRepository.findAll();

        return getAllNotifications;
    }

    @GetMapping("/Notifications/Users/{username}")
    List<Notification> getAllNotificationsByUsername(@PathVariable String username)
            throws ResourceNotFoundException {
        User retrievedUser = userRepository.findByUserName(username);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("Username cannot be found!");
        }

        List<Notification> getAllUserNotifications = notificationRepository.findByUserNotification_userName(username);

        return getAllUserNotifications;
    }

    @PutMapping("/Users/{username}/Notifications/{id}")
    Notification editNotification(@PathVariable String username, @PathVariable int id)
            throws ResourceNotFoundException {
        Notification retrievedNotification = notificationRepository.findById(id);

        if(retrievedNotification == null){
            throw new ResourceNotFoundException("No notification found");
        }

        User retrieveUser = userRepository.findByUserName(username);

        retrievedNotification.setHasRead(true);

        notificationRepository.save(retrievedNotification);

        for(Notification notification: retrieveUser.getUserNotifications()){
            if(notification.getId() == retrievedNotification.getId()){
                notification.setHasRead(true);
            }
        }

        userRepository.save(retrieveUser);

        return retrievedNotification;
    }
}
