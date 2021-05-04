package com309.springboot.isumarketplace.Repository;

import com309.springboot.isumarketplace.Model.Notification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findById(int id);
    List<Notification> findByUserNotification_userName(String username);
    List<Notification> findByIdAndUserNotification_userName(int id, String username);
    List<Notification> findByUserNotification_userName(String username, Sort sort);
}
