package com309.springboot.isumarketplace;

import com309.springboot.isumarketplace.Model.Mail;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.MailRepository;
import com309.springboot.isumarketplace.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Mail.class)
public class TestingMailControllerIntegration {
    @TestConfiguration
    static class UserContextConfiguration {
        @Bean
        UserRepository getUserRepository() {
            return mock(UserRepository.class);
        }
        @Bean
        MailRepository getMailRepository() { return mock(MailRepository.class); }
    }

    @Autowired
    private MockMvc controller;
    @Autowired
    private UserRepository userRepository;
    @Autowired MailRepository mailRepository;

//    @Test
//    public void testSendMail() throws Exception {
//        List<User> userMockList = new ArrayList<>();
//        List<Mail> mailMockList = new ArrayList<>();
//
//        User createTestUser = new User("Johnny", "Bravo", "mkhairi@iastate.edu",
//                "Johnny", "Johnny1234", "2020-11-19");
//
//        userMockList.add(createTestUser);
//
//        //Mocking the behavior of the function ie; how it would response
//        when(userRepository.findByEmailAddress(anyString()))
//                .thenAnswer(x -> {
//                    String emailAddress = x.getArgument(0);
//
//                    for(User user: userMockList){
//                        if(user.getEmailAddress().equals(emailAddress)){
//                            return user;
//                        }
//                    }
//
//                    return null;
//                });
//
//        when(mailRepository.save(any(Mail.class)))
//                .thenAnswer(x -> {
//                    Mail mail = x.getArgument(0);
//
//                    mailMockList.add(mail);
//
//                    return null;
//                });
//
//        when(userRepository.save(any(User.class)))
//                .thenAnswer(x -> {
//                    User user = x.getArgument(0);
//
//                    for(User user1: userMockList){
//                        if(user1 == user){
//                            user1 = user;
//                            return user1;
//                        }
//                    }
//
//                    return null;
//                });
//
//        controller.perform(get("/Mail/SendMail").param("emailAddress", "mkhairi@iastate.edu")
//                .param("name", "John Doe")).andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Hello World John Doe!!!"))
//                .andExpect(jsonPath("$.id").value(1));
////        controller.perform(get("/Mail/SendMail")
////                .param("emailAddress", "mkhairi@iastate.edu"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.message").value("An email is successfully sent to the email address!"));
//
////        controller.perform(get("/Mail/SendMail?emailAddress={emailAddress}", "mkhairi@iastate.edu").contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.httpCodeMessage").value("OK"))
////                .andExpect(jsonPath("$.message").value("An email is successfully sent to the email address!"));
//
//
//    }
}
