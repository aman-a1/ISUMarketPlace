package com309.springboot.isumarketplace.Controller;

import com309.springboot.isumarketplace.Exceptions.OnFailedException;
import com309.springboot.isumarketplace.Exceptions.OnSuccessException;
import com309.springboot.isumarketplace.Exceptions.ResourceNotFoundException;
import com309.springboot.isumarketplace.Model.Mail;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.MailRepository;
import com309.springboot.isumarketplace.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@RestController
public class MailController {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/Mail/SendMail")
    public String sendMail(@RequestParam String emailAddress) throws ResourceNotFoundException, OnFailedException, OnSuccessException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        User getUser = userRepository.findByEmailAddress(emailAddress);

        if(getUser == null){
            throw new ResourceNotFoundException("No account associated with the provided email");
        }

        String userNewPassword = getSaltString();

        getUser.setUserPassword(userNewPassword);

        userRepository.save(getUser);

        String bodyText = "Your password has been reset. Please sign in with new password\nNew password is "
                + userNewPassword + "\n\n\n\n" + "** Note: Please change to a new password after log in";

        String subject = "ISU Market Place: " + getUser.getUserName() + " forgot password";

        try {

            helper.setTo(emailAddress);
            helper.setText(bodyText);
            helper.setSubject(subject);

        } catch (MessagingException e) {
            e.printStackTrace();

            throw new OnFailedException("Error sending mail");
        }

        Mail sentMail = new Mail(emailAddress, bodyText, subject);

        sender.send(message);
        mailRepository.save(sentMail);

        throw new OnSuccessException("An email is successfully sent to the email address!");

    }

    @GetMapping("/Mails")
    public List<Mail> getAllSentMail(){
        return mailRepository.findAll();
    }

    @GetMapping("/MailsToUsername")
    public List<Mail> getAllSentMailToUserName(@RequestParam String username) throws ResourceNotFoundException {
        User getUser = userRepository.findByUserName(username);

        if(getUser == null){
            throw new ResourceNotFoundException("No username found");
        }

        List<Mail> allMailSentToUsername = mailRepository.findByRecipientEmail(getUser.getEmailAddress());

        return allMailSentToUsername;
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
