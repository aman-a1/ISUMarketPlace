package com.example.myapplication;

/**
* This activity is the logic part to change the password of your account.
* @author Abdula Eljaam (abeljaam)
*/


import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SecurityLogic implements IVolleyListener{

    IView d;
    IServerRequest r;

   /**
    * Constructor to create an instance of SecurityLogic.
    * @param d The IView given.
    * @param r The IServerRequest given
    */


    public SecurityLogic(IView d, IServerRequest r){
        this.d = d;
        this.r = r;
        r.addIVolleyListener(this);
    }
    
     /**
    * Method to insert inputted paramters in a user object and send request to the server.
    * @param username username of account.
    * @param oldPassword current password.
    * @param password new password.
    * @return Nothing.
    */

    public void changePassword(String username, String oldPassword,String password) {
        String url = "http://coms-309-vb-05.cs.iastate.edu:8080/User/"+username+"/ChangePassword";

        JSONObject user_object = new JSONObject();

        try {
            user_object.put("username",username);
            user_object.put("oldPassword", oldPassword);
            user_object.put("newPassword", password);
        }catch (JSONException e){
            e.printStackTrace();
        }
        r.sendToServer(url, user_object, "PUT");
    }
    
      /**
    * Method to check the inputted paramters if the current password is correct, and if the new password passes the validation requirements.
    * @param first Current password entered
    * @param second New password entered
    * @param password Current password of user
    * @return Response message.
    */

    public String checkInputChange(String first, String second, String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

        if(!first.equals(password) && second.matches(regex)){
            d.toastText("You have incorrectly entered your current password!");
            return "You have incorrectly entered your current password!";
        }

        else if(!second.matches(regex) && first.equals(password)){
            d.toastText("Your new password has not met all conditions: contains at least 8 characters and at most 20 characters, contains at least one digit, contains at least one upper case alphabet, and contains at least one lower case alphabet.");
            return "Your new password has not met all conditions: contains at least 8 characters and at most 20 characters, contains at least one digit, contains at least one upper case alphabet, and contains at least one lower case alphabet.";
        }

        else if(first.equals(password) && second.matches(regex)){
            d.toastText("You have successfully entered the data!");
            return "You have successfully entered the data!";
        }

        else if(!first.equals(password) && !second.matches(regex)){
            d.toastText("You have incorrectly typed your current passsword and your new one does not" +
                    " follow the following requirements: contains at least 8 characters and at most 20 characters, It contains at least one digit, It contains at least one upper case alphabet, It contains at least one lower case alphabet.");
            return "You have incorrectly typed your current passsword and your new one does not " +
                    "follow the following requirements: contains at least 8 characters and at most 20 characters, It contains at least one digit, It contains at least one upper case alphabet, It contains at least one lower case alphabet.";
        }

        return null;
    }
    
     /**
    * Method to show or hide the text entered in the new password edit text.
    * @param i Integer to know if even or odd
    * @param show Button to show or hide
    * @param t The EditText that is used to enter new password
    * @return Nothing.
    */


    public void showHide(int i, Button show, EditText t){
        if(i%2 == 0) {
            t.setTransformationMethod(null);
            show.setText("HIDE");
        }
        else{
            t.setTransformationMethod(new PasswordTransformationMethod());
            show.setText("SHOW");
        }
    }
    
    /**
    * Method to control if request was a success.
    * @param getResponseMessage Respsonse Message
    * @return Nothing.
    */

    @Override
    public void onSuccess(String getResponseMessage) {
        d.toastText(getResponseMessage);
    }
    
      /**
    * Method to control if request was an error.
    * @param error The error given by server
    * @return Nothing.
    */

    @Override
    public void onError(VolleyError error) {
        String body;
        String getResponseMessage;
        JSONObject bodyJsonObject;
        String statusCode = String.valueOf(error.networkResponse.statusCode);

        if(statusCode.equals("401")){

            if(error.networkResponse.data != null){
                try{
                    body = new String(error.networkResponse.data, "UTF-8");
                    bodyJsonObject = new JSONObject(body);
                    getResponseMessage = bodyJsonObject.getString("message");
                    d.toastText(getResponseMessage);
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        else if(statusCode.equals("404")){

            if(error.networkResponse.data != null){
                try{
                    body = new String(error.networkResponse.data, "UTF-8");
                    bodyJsonObject = new JSONObject(body);
                    getResponseMessage = bodyJsonObject.getString("message");
                    d.toastText(getResponseMessage);
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        else{
            Log.d("Error.Response", String.valueOf(error));
        }
    }




}
