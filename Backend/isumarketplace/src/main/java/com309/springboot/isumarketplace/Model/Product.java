package com309.springboot.isumarketplace.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Product id", name = "id", value = "Integer")
    Integer id;
    @ApiModelProperty(notes = "Product name", name = "productName", required = true, value = "String")
    @Column(nullable = false)
    String productName;
    @ApiModelProperty(notes = "Product description", name = "productDescription", required = true, value = "String")
    @Column(nullable = false)
    String productDescription;
    @ApiModelProperty(notes = "Product price", name = "productPrice", required = true, value = "Double")
    @Column(nullable = false)
    Double productPrice;
    @ApiModelProperty(notes = "Product quantity", name = "productQuantity", required = true, value = "Integer")
    @Column(nullable = false)
    Integer productQuantity;
    @ApiModelProperty(notes = "Product category", name = "productCategory", required = true, value = "Integer")
    @Column(nullable = false)
    String productCategory;
    @ApiModelProperty(notes = "Product date posted", name = "datePosted", required = true, value = "Date(yyyy-MM-dd)")
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date datePosted;
    @ApiModelProperty(notes = "Product status", name = "productStatus", required = true, value = "Boolean")
    @Column(nullable = false)
    Boolean productStatus;
    @ApiModelProperty(notes = "Product owner username", name = "ownerUsername", required = true, value = "String")
    @Column
    String ownerUsername;
    @ApiModelProperty(notes = "Product owner(Many-to-one relationship)", name = "productOwner", required = true, value = "User")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User productOwner;
    @ApiModelProperty(notes = "Product images(One-to-many relationship)", name = "productImages", required = true, value = "Set<Images>")
    @OneToMany(mappedBy = "productPicture")
    Set<Image> productImages;

    public Product(){}

    public Product(String productName, String productDescription, double productPrice, int productQuantity,
                   String productCategory, String datePosted, boolean productStatus, String ownerUsername) throws ParseException {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productCategory = productCategory;
        this.productStatus = productStatus;
        this.datePosted = new SimpleDateFormat("yyyy-MM-dd").parse(datePosted);
        this.ownerUsername = ownerUsername;
    }

    public Product(String productName, String productDescription, double productPrice, int productQuantity,
                   String productCategory, String datePosted, boolean productStatus, String ownerUsername, User productOwner) throws ParseException {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productCategory = productCategory;
        this.productStatus = productStatus;
        this.datePosted = new SimpleDateFormat("yyyy-MM-dd").parse(datePosted);
        this.ownerUsername = ownerUsername;
        this.productOwner = productOwner;
    }

    public Integer getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public Boolean getProductStatus() {
        return productStatus;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public User getProductOwner() {
        return productOwner;
    }

    public Set<Image> getProductImages() {
        return productImages;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }

    public void setProductOwner(User productOwner) {
        this.productOwner = productOwner;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public void setProductImages(Set<Image> productImages) {
        this.productImages = productImages;
    }

    public void addProductImage(Image productImage){
        this.productImages.add(productImage);
    }
}
