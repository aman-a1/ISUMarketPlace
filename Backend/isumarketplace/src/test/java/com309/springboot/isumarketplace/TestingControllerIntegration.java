package com309.springboot.isumarketplace;

import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.ImageRepository;
import com309.springboot.isumarketplace.RequestBody.EditProfileBody;
import com309.springboot.isumarketplace.RequestBody.LoginAuthentication;
import org.junit.Test;
import org.junit.runner.RunWith;

import com309.springboot.isumarketplace.Controller.UserController;
import com309.springboot.isumarketplace.Repository.UserRepository;
import org.mockito.Mockito;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class TestingControllerIntegration {
    @TestConfiguration
    static class UserContextConfiguration {
        @Bean
        UserRepository getUserRepository() {
            return mock(UserRepository.class);
        }
        @Bean
        ImageRepository getImageRepository() { return mock(ImageRepository.class); }
    }

    class CreateUser{
        String firstName;
        String lastName;
        String emailAddress;
        String userName;
        String userPassword;
        String dateCreated;

        public CreateUser(String firstName, String lastName, String emailAddress,
                          String userName, String userPassword, String dateCreated){
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.userName = userName;
            this.userPassword = userPassword;
            this.dateCreated = dateCreated;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public String getDateCreated() {
            return dateCreated;
        }
    }

    @Autowired
    private MockMvc controller;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() throws Exception {
        List<User> mockList = new ArrayList<User>();

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: mockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        // mock the save() method to save argument to the list
        when(userRepository.save(any(User.class)))
                .thenAnswer(x -> {
                    User user = x.getArgument(0);
                    mockList.add(user);
                    return null;
                });

        CreateUser createUser = new CreateUser("test1", "testing1",
                "test1@test.com", "test1", "password", "2020-10-29");

        Gson gson = new Gson();
        String createUserJson = gson.toJson(createUser);

        controller.perform(post("/CreateUser").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("test1")))
                .andExpect(jsonPath("$.lastName", is("testing1")))
                .andExpect(jsonPath("$.userName", is("test1")));

    }

    @Test
    public void testPostDuplicateUser() throws Exception {
        List<User> mockList = new ArrayList<User>();

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: mockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        // mock the save() method to save argument to the list
        when(userRepository.save(any(User.class)))
                .thenAnswer(x -> {
                    User user = x.getArgument(0);
                    mockList.add(user);
                    return null;
                });

        CreateUser createUser = new CreateUser("test1", "testing1",
                "test1@test.com", "test1", "password", "2020-10-29");

        Gson gson = new Gson();
        String createUserJson = gson.toJson(createUser);

        controller.perform(post("/CreateUser").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("test1")))
                .andExpect(jsonPath("$.lastName", is("testing1")))
                .andExpect(jsonPath("$.userName", is("test1")));

        controller.perform(post("/CreateUser").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testEditNonExistentUser() throws Exception {
        List<User> mockList = new ArrayList<User>();

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: mockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        EditProfileBody editProfileBody = new EditProfileBody();

        editProfileBody.setFirstName("Testo");
        editProfileBody.setLastName("Tiesto");

        Gson gson = new Gson();
        String createUserJson = gson.toJson(editProfileBody);

        controller.perform(put("/User/Testo").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testEditExistentUser() throws Exception {
        List<User> mockList = new ArrayList<User>();

        User createUser = new User("test1", "testing1",
                "test1@test.com", "test1", "password", "2020-10-29");

        mockList.add(createUser);

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: mockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        // mock the save() method to save argument to the list
        when(userRepository.save(any(User.class)))
                .thenAnswer(x -> {
                    User user = x.getArgument(0);

                    for(User user1: mockList){
                        if(user1.getUserName().equals(user.getUserName())){
                            user1.setFirstName(user.getFirstName());
                            user1.setLastName(user.getLastName());
                        }
                    }

                    return null;
                });

        EditProfileBody editProfileBody = new EditProfileBody();

        editProfileBody.setFirstName("Testo");
        editProfileBody.setLastName("Tiesto");

        Gson gson = new Gson();
        String createEditProfileJson = gson.toJson(editProfileBody);

        controller.perform(put("/User/test1").contentType(MediaType.APPLICATION_JSON).content(createEditProfileJson))
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteExistentUser() throws Exception {
        List<User> mockList = new ArrayList<User>();

        // Add a user to mock list
        User createUser = new User("test1", "testing1",
                "test1@test.com", "test1", "password", "2020-10-29");

        mockList.add(createUser);

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: mockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        LoginAuthentication loginAuthentication = new LoginAuthentication();
        loginAuthentication.setUsername("test1");
        loginAuthentication.setPassword("password");

        Gson gson = new Gson();
        String createLoginAuthentication = gson.toJson(loginAuthentication);

        controller.perform(post("/Users/Delete/test1").contentType(MediaType.APPLICATION_JSON).content(createLoginAuthentication))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNonExistentUser() throws Exception {
        List<User> mockList = new ArrayList<User>();

        // Add a user to mock list
        User createUser = new User("test1", "testing1",
                "test1@test.com", "test1", "password", "2020-10-29");

        mockList.add(createUser);

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: mockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        LoginAuthentication loginAuthentication = new LoginAuthentication();
        loginAuthentication.setUsername("test1");
        loginAuthentication.setPassword("password");

        Gson gson = new Gson();
        String createLoginAuthentication = gson.toJson(loginAuthentication);

        controller.perform(post("/Users/Delete/test").contentType(MediaType.APPLICATION_JSON).content(createLoginAuthentication))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUserWrongCredentials() throws Exception {
        List<User> mockList = new ArrayList<User>();

        // Add a user to mock list
        User createUser = new User("test1", "testing1",
                "test1@test.com", "test1", "password", "2020-10-29");

        mockList.add(createUser);

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: mockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        LoginAuthentication loginAuthentication = new LoginAuthentication();
        loginAuthentication.setUsername("test1");
        loginAuthentication.setPassword("password123");

        Gson gson = new Gson();
        String createLoginAuthentication = gson.toJson(loginAuthentication);

        controller.perform(post("/Users/Delete/test1")
                .contentType(MediaType.APPLICATION_JSON).content(createLoginAuthentication))
                .andExpect(status().isUnauthorized());
    }
}
