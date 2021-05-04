package com309.springboot.isumarketplace.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Table(name = "User_ISUMarketplace")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "User Id", name = "id", value = "Integer")
    Integer id;
    @ApiModelProperty(notes = "User first name", name = "firstName", required = true, value = "String")
    @Column(nullable = false)
    String firstName;
    @ApiModelProperty(notes = "User last name", name = "lastName", required = true, value = "String")
    @Column(nullable = false)
    String lastName;
    @ApiModelProperty(notes = "User email address", name = "emailAddress", required = true, value = "String")
    @Column(nullable = false)
    String emailAddress;
    @ApiModelProperty(notes = "User username", name = "userName", required = true, value = "String")
    @Column(nullable = false)
    String userName;
    @ApiModelProperty(notes = "User password", name = "userPassword", required = true, value = "String")
    @Column(nullable = false)
    String userPassword;
    @ApiModelProperty(notes = "User rating", name = "userRating", required = true, value = "Integer")
    @Column
    int userRating;
    @ApiModelProperty(notes = "User account date created", name = "dateCreated", required = true, value = "date(yyyy-MM-dd)")
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date dateCreated;
    @ApiModelProperty(notes = "User profile picture", name = "profilePicture", required = true, value = "Image")
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "userDp")
    Image profilePicture;
    @ApiModelProperty(notes = "User products(one-to-many relationship)", name = "userProducts", required = true, value = "Set<Product>")
    @OneToMany(mappedBy = "productOwner")
    Set<Product> userProducts;
    @ApiModelProperty(notes = "User notifications(one-to-many relationship)", name = "userNotifications", required = true, value = "Set<Notification>")
    @OneToMany(mappedBy = "userNotification")
    Set<Notification> userNotifications;
    @OneToMany(mappedBy = "conversationWith")
    @JsonIgnore
    Set<UserConversation> userConversations;

    public User() {}

    public User(String firstName, String lastName, String emailAddress, String userName,
                String userPassword, String dateCreated) throws ParseException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.userPassword = userPassword;
        this.dateCreated = new SimpleDateFormat("yyyy-MM-dd").parse(dateCreated);
        userProducts = new HashSet<>();

    }

    public Integer getId() {
        return id;
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

    public int getUserRating() {
        return userRating;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public Set<Product> getUserProducts() {
        return userProducts;
    }

    public Set<Notification> getUserNotifications() {
        return userNotifications;
    }

    public Set<UserConversation> getUserConversations() {
        return userConversations;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setUserProducts(Set<Product> userProducts) {
        this.userProducts = userProducts;
    }

    public void addProduct(Product userProduct) {
        this.userProducts.add(userProduct);
    }

    public void setUserNotifications(Set<Notification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public void setUserConversations(Set<UserConversation> userConversations) {
        this.userConversations = userConversations;
    }

    public void addUserNotifications(Notification userNotification){
        this.userNotifications.add(userNotification);
    }

    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
