package com309.springboot.isumarketplace.RequestBody;

public class EditProfileBody {
    String firstName;
    String lastName;
    String imageName;
    String imageType;
    String imageString;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImageString() {
        return imageString;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
