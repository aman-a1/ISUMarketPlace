package com309.springboot.isumarketplace.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.sql.LobTypeMappings;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Arrays;

@Entity
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Image id", name = "id", value = "Integer")
    Integer id;
    @ApiModelProperty(notes = "Image name", name = "imageName", required = true, value = "String")
    @Column
    String imageName;
    @ApiModelProperty(notes = "Image type", name = "imageType", required = true, value = "String")
    @Column
    String imageType;
    @ApiModelProperty(notes = "Image string", name = "imageString", required = true, value = "String")
    @Column
    String imageString;
    @ApiModelProperty(notes = "Image byte", name = "imageByte", required = true, value = "byte[]")
    @Lob
    @Column(columnDefinition="mediumblob")
    byte[] imageByte;
    @ApiModelProperty(notes = "Owner profile picture(one-to-one relationship)", name = "userDp", required = true, value = "User")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User userDp;
    @ApiModelProperty(notes = "Product images(Many-to-one relationship)", name = "productPicture", required = true, value = "Product")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Product productPicture;


    public Image(){
        super();
    }

    public Image(String imageName, String imageType, byte[] imageByte){
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageByte = imageByte;
    }

    public Image(String imageName, String imageType, String imageString){
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageString = imageString;
    }

    public Integer getId() {
        return id;
    }

    public String getImageName() {
        return this.imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImageString() {
        return imageString;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public User getUserDp() {
        return userDp;
    }

    public Product getProductPicture() {
        return productPicture;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public void setUserDp(User userDp) {
        this.userDp = userDp;
    }

    public void setProductPicture(Product productPicture) {
        this.productPicture = productPicture;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", userDp=" + userDp +
                ", productPicture=" + productPicture +
                '}';
    }
}
