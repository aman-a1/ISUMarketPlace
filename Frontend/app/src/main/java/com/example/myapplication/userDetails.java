package com.example.myapplication;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * @author amana1
 *
 * class object used to hold user's credentials and otehr details
 */
public class userDetails implements Serializable
{
    public String fname;
    public String lname;
    public String username;
    public String password;
    public String emailAddress;
    public String date;
    public Bitmap image = null;


    public userDetails(String fname,
                       String lname,
                       String username,
                       String password,
                       String emailAddress, String date)
    {
        this.fname = fname;
        this.emailAddress=emailAddress;
        this.password=password;
        this.lname = lname;
        this.username=username;
        this.date = date;
    }

}
