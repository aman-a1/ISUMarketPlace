package com309.springboot.isumarketplace;

import com.google.gson.Gson;
import com309.springboot.isumarketplace.Controller.ProductController;
import com309.springboot.isumarketplace.Model.Notification;
import com309.springboot.isumarketplace.Model.Product;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class TestingProductControllerIntegration {
    @TestConfiguration
    static class ProductContextConfiguration {
        @Bean
        UserRepository getUserRepository() {
            return mock(UserRepository.class);
        }
        @Bean
        ImageRepository getImageRepository() { return mock(ImageRepository.class); }
        @Bean
        ProductRepository getProductRepository() { return mock(ProductRepository.class); }
        @Bean
        ProductCategoryRepository getProductCategoryRepository() { return mock(ProductCategoryRepository.class); }
        @Bean
        NotificationRepository getNotificationRepository() { return mock(NotificationRepository.class); }
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

    class CreateProduct{
        String productName;
        String productDescription;
        double productPrice;
        int productQuantity;
        String productCategory;
        String datePosted;
        Boolean productStatus;
        int id;

        public CreateProduct(String productName, String productDescription, double productPrice, int productQuantity,
                             String productCategory, String datePosted, Boolean productStatus){
            this.productName = productName;
            this.productDescription = productDescription;
            this.productPrice = productPrice;
            this.productQuantity = productQuantity;
            this.productCategory = productCategory;
            this.datePosted = datePosted;
            this.productStatus = productStatus;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public String getProductCategory() {
            return productCategory;
        }

        public String getDatePosted() {
            return datePosted;
        }

        public Boolean getProductStatus() {
            return productStatus;
        }
    }

    @Autowired
    private MockMvc controller;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void testSaveProduct() throws Exception {
        List<User> userMockList = new ArrayList<>();
        List<Product> productMockList = new ArrayList<>();
        List<Notification> notificationsMockList = new ArrayList<>();

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: userMockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        when(userRepository.findAll())
                .thenReturn(userMockList);

        // mock the save() method to save argument to the list
        when(userRepository.save(any(User.class)))
                .thenAnswer(x -> {
                    User user = x.getArgument(0);

                    for(User user1: userMockList){
                        if(user1.getUserName().equals(user.getUserName())){
                            user1 = user;
                        }
                    }
                    //userMockList.add(user);
                    return null;
                });

        when(productRepository.save(any(Product.class)))
                .thenAnswer(x -> {
                   Product product = x.getArgument(0);
                   productMockList.add(product);
                   return null;
                });

        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(x -> {
                   Notification notification = x.getArgument(0);
                   notificationsMockList.add(notification);
                   return null;
                });

        User createTestUser = new User("Johnny", "Bravo", "Johnny@iastate.edu",
                "Johnny", "Johnny1234", "2020-11-19");

        userMockList.add(createTestUser);

        CreateProduct product = new CreateProduct("TestProduct", "Product Test",
                40.0, 1, "All", "2020-11-19", true);

        Gson gson = new Gson();
        String createUserJson = gson.toJson(product);

        controller.perform(post("/users/Johnny/products").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpCodeMessage", is("OK")))
                .andExpect(jsonPath("$.message", is("You have successfully upload a product")));
    }

    @Test
    public void testSaveProductNoUser() throws Exception{
        List<User> userMockList = new ArrayList<>();
        List<Product> productMockList = new ArrayList<>();

        //Mocking the behavior of the function ie; how it would response
        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: userMockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        CreateProduct product = new CreateProduct("TestProduct", "Product Test",
                40.0, 1, "All", "2020-11-19", true);

        Gson gson = new Gson();
        String createUserJson = gson.toJson(product);

        controller.perform(post("/users/Johnny/products").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Username not found")));

    }

    @Test
    public void testFindProductById() throws Exception{
        List<Product> productMockList = new ArrayList<>();

        Product product = new Product("TestProduct", "Product Test",
                40.0, 1, "All", "2020-11-19", true, "Johnny");

        product.setId(1);

        productMockList.add(product);

        when(productRepository.findById(anyInt()))
                .thenAnswer(x -> {
                   int id = x.getArgument(0);

                   for(Product product1: productMockList){
                       if(product1.getId() == id){
                           return Optional.of(product1);
                       }
                   }

                   return null;
                });



        Gson gson = new Gson();
        String createUserJson = gson.toJson(product);

        controller.perform(get("/Products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is(product.getProductName())))
                .andExpect(jsonPath("$.productDescription", is(product.getProductDescription())))
                .andExpect(jsonPath("$.productPrice", is(product.getProductPrice())))
                .andExpect(jsonPath("$.productQuantity", is(product.getProductQuantity())))
                .andExpect(jsonPath("$.productCategory", is(product.getProductCategory())));
    }

    @Test
    public void testModifyProduct() throws Exception{
        List<Product> productMockList = new ArrayList<>();
        List<User> userMockList = new ArrayList<>();

        Product product = new Product("TestProduct", "Product Test",
                40.0, 1, "All", "2020-11-19", true, "Johnny");

        product.setId(1);

        User createTestUser = new User("Johnny", "Bravo", "mkhairi@iastate.edu",
                "Johnny", "Johnny1234", "2020-11-19");

        createTestUser.addProduct(product);
        product.setProductOwner(createTestUser);

        productMockList.add(product);
        userMockList.add(createTestUser);

        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: userMockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        when(productRepository.findById(anyInt()))
                .thenAnswer(x -> {
                    int id = x.getArgument(0);

                    for(Product product1: productMockList){
                        if(product1.getId().equals(id)){
                            return Optional.of(product1);
                        }
                    }

                    return null;
                });

        when(productRepository.save(any(Product.class)))
                .thenAnswer(x -> {
                    Product product1 = x.getArgument(0);

                    for(Product product2: productMockList){
                        if(product2 == product1){
                            product2 = product1;

                            return product2;
                        }
                    }

                    return null;
                });

        CreateProduct modifiedProduct = new CreateProduct("TestProductModified", "Product Test Modified",
                40.0, 1, "All", "2020-11-19", true);

        Gson gson = new Gson();
        String createProductJson = gson.toJson(modifiedProduct);

        controller.perform(put("/users/Johnny/products/1").contentType(MediaType.APPLICATION_JSON).content(createProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is(modifiedProduct.getProductName())));

    }

    @Test
    public void testGetProductsByUsername() throws Exception{
        List<Product> productMockList = new ArrayList<>();
        List<User> userMockList = new ArrayList<>();

        Product product = new Product("TestProduct", "Product Test",
                40.0, 1, "All", "2020-11-19", true, "Johnny");

        product.setId(1);

        User createTestUser = new User("Johnny", "Bravo", "mkhairi@iastate.edu",
                "Johnny", "Johnny1234", "2020-11-19");

        createTestUser.addProduct(product);
        product.setProductOwner(createTestUser);

        productMockList.add(product);
        userMockList.add(createTestUser);

        when(productRepository.findByProductOwner_userName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);
                    List<Product> retVal = new ArrayList<>();
                    for(Product product1: productMockList){
                        if(product1.getOwnerUsername().equals(username)){
                            retVal.add(product1);
                        }
                    }

                    return retVal;
                });

        when(userRepository.findByUserName(anyString()))
                .thenAnswer(x -> {
                    String username = x.getArgument(0);

                    for(User user: userMockList){
                        if(user.getUserName().equals(username)){
                            return user;
                        }
                    }

                    return null;
                });

        controller.perform(get("/users/Johnny/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName", is(product.getProductName())))
                .andExpect(jsonPath("$[0].productDescription", is(product.getProductDescription())))
                .andExpect(jsonPath("$[0].productPrice", is(product.getProductPrice())))
                .andExpect(jsonPath("$[0].productQuantity", is(product.getProductQuantity())))
                .andExpect(jsonPath("$[0].productCategory", is(product.getProductCategory())));

    }
}
