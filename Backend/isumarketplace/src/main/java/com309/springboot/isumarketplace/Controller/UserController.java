package com309.springboot.isumarketplace.Controller;

import com309.springboot.isumarketplace.Exceptions.*;
import com309.springboot.isumarketplace.Model.Image;
import com309.springboot.isumarketplace.Model.Product;
import com309.springboot.isumarketplace.Repository.ImageRepository;
import com309.springboot.isumarketplace.RequestBody.ChangeUserPassword;
import com309.springboot.isumarketplace.RequestBody.EditProfileBody;
import com309.springboot.isumarketplace.RequestBody.LoginAuthentication;
import com309.springboot.isumarketplace.Model.User;
import com309.springboot.isumarketplace.Repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    UserRepository UserRepo;
    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/CreateUser")
    User createUser(@RequestBody User user) throws RegistrationFailedExeption {
        User retrievedUser = UserRepo.findByUserName(user.getUserName());

        if(retrievedUser != null){
            throw new RegistrationFailedExeption("Invalid email! Email has been registered");
        }else{
            UserRepo.save(user);

            return user;
        }
    }

    @GetMapping("/GetAllUser")
    List<User> getAllUser(){
        List<User> getAllUsers = UserRepo.findAll();

        for(User user: getAllUsers){
            if(user.getProfilePicture() != null){
                Image userProfilePicture = user.getProfilePicture();
                String newImageString = new String(userProfilePicture.getImageByte());
                Image newProfilePicture = new Image(userProfilePicture.getImageName(),
                        userProfilePicture.getImageType(), newImageString);

                user.setProfilePicture(newProfilePicture);
            }

            if(!user.getUserProducts().isEmpty()){
                Set<Product> userProducts = user.getUserProducts();

                for(Product product: userProducts){
                    if(!product.getProductImages().isEmpty()){
                        Set<Image> productImages = product.getProductImages();
                        Set<Image> newProductImage = new HashSet<Image>();

                        for(Image image: productImages){
                            String newImageString = new String(image.getImageByte());

                            Image newImage = new Image(image.getImageName(), image.getImageType(), newImageString);

                            newProductImage.add(newImage);
                        }

                        product.setProductImages(newProductImage);
                    }
                }
            }
        }

        return getAllUsers;
    }

    @GetMapping("/Users/{username}")
    User getUser(@PathVariable String username) throws ResourceNotFoundException {
        User retrievedUser = UserRepo.findByUserName(username);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("No username Found");
        }

        Image getUserImage = retrievedUser.getProfilePicture();

        if(getUserImage != null){
            String imageString = new String(getUserImage.getImageByte());
            Image newImage = new Image(getUserImage.getImageName(), getUserImage.getImageType(), imageString);
            retrievedUser.setProfilePicture(newImage);
        }

        Set<Product> userProducts = retrievedUser.getUserProducts();

        if(!userProducts.isEmpty()){
            for(Product product: userProducts){
                Set<Image> productImages = product.getProductImages();
                Set<Image> newProductImage = new HashSet<Image>();

                if(!productImages.isEmpty()){
                    for(Image image: productImages){
                        String productImageString = new String(image.getImageByte());
                        Image newImage = new Image(image.getImageName(), image.getImageType(), productImageString);

                        newProductImage.add(newImage);
                    }
                }

                product.setProductImages(newProductImage);
            }
        }

        return retrievedUser;

    }

    @PostMapping("/AuthenticateLogin")
    User validateLogin(@RequestBody LoginAuthentication loginAuthentication) throws UserAuthenticationFailedException{
        User retrievedUser = UserRepo.findByUserName(loginAuthentication.getUsername());

        if(retrievedUser == null){
            throw new UserAuthenticationFailedException("Invalid Username or Password");
        }else{
            if(!retrievedUser.getUserPassword().equals(loginAuthentication.getPassword())){
                throw new UserAuthenticationFailedException("Invalid Username or Password");
            }else{
                User postUser = new User();

                postUser.setId(retrievedUser.getId());
                postUser.setFirstName(retrievedUser.getFirstName());
                postUser.setLastName(retrievedUser.getLastName());
                postUser.setEmailAddress(retrievedUser.getEmailAddress());
                postUser.setProfilePicture(retrievedUser.getProfilePicture());
                postUser.setUserName(retrievedUser.getUserName());
                postUser.setUserPassword(null);
                postUser.setUserRating(retrievedUser.getUserRating());
                postUser.setDateCreated(retrievedUser.getDateCreated());
                postUser.setUserProducts(retrievedUser.getUserProducts());

                return postUser;
            }
        }
    }

    @PutMapping("/User/{username}/ChangePassword")
    User changePassword(@PathVariable String username, @RequestBody ChangeUserPassword changeUserPassword)
            throws UserAuthenticationFailedException, OnSuccessException {
        User retrievedUser = UserRepo.findByUserName(username);

        if(retrievedUser == null){
            throw new UserAuthenticationFailedException("Invalid Username of Password");
        }

        if(retrievedUser.getUserPassword().equals(changeUserPassword.getOldPassword())){
            retrievedUser.setUserPassword(changeUserPassword.getNewPassword());
            UserRepo.save(retrievedUser);
            throw new OnSuccessException("New password has successfully updated!");
        }else{
            throw new UserAuthenticationFailedException("Old Password is Wrong");
        }

    }

    @PutMapping("/User/{username}")
    public User updateUser(@PathVariable String username, @RequestBody EditProfileBody editProfileBody)
            throws ResourceNotFoundException, OnSuccessException {

        User retrievedUser = UserRepo.findByUserName(username);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("No username Found");
        }

        retrievedUser.setFirstName(editProfileBody.getFirstName());
        retrievedUser.setLastName(editProfileBody.getLastName());

        if(editProfileBody.getImageName() != null &&
                editProfileBody.getImageType() != null &&
                editProfileBody.getImageString() != null){
            Image newImage = new Image(editProfileBody.getImageName(), editProfileBody.getImageType(), editProfileBody.getImageString().getBytes());
            retrievedUser.setProfilePicture(newImage);
            newImage.setUserDp(retrievedUser);
            imageRepository.save(newImage);
        }

        UserRepo.save(retrievedUser);

        throw new OnSuccessException("Your details have been updated successfully!");

    }

    @PostMapping("/Users/Delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username, @RequestBody LoginAuthentication loginAuthentication) throws ResourceNotFoundException, UserAuthenticationFailedException, OnSuccessException {
        User retrievedUser = UserRepo.findByUserName(username);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("No username found");
        }

        if(loginAuthentication.getUsername().equals(username)){
            if(!retrievedUser.getUserPassword().equals(loginAuthentication.getPassword())){
                throw new UserAuthenticationFailedException("Invalid Username or Password");
            }else{
                UserRepo.delete(retrievedUser);

                throw new OnSuccessException("Your account have been deleted successfully!");
            }
        }else{
            throw new UserAuthenticationFailedException("Invalid username or Password");
        }

    }

}
