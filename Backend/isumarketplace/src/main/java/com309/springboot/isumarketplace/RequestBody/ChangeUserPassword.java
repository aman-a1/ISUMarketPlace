package com309.springboot.isumarketplace.RequestBody;

public class ChangeUserPassword {
    String username;
    String oldPassword;
    String newPassword;

    public String getUsername() {
        return username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
