package com309.springboot.isumarketplace.Repository;

import com309.springboot.isumarketplace.Model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Long> {
    List<Mail> findByRecipientEmail(String emailAddress);
}
