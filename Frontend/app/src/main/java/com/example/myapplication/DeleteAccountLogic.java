package com.example.myapplication;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Interface for a volley listener.
 */


interface IVolleyListener {
    public void onSuccess(String getResponseMessage);
    public void onError(VolleyError error);
}


/**
* This activity is the logic part to delete the user's account.
* @author Abdula Eljaam (abeljaam)
*/



public class DeleteAccountLogic implements IVolleyListener {

    IView d;
    IServerRequest r;
    
       /**
    * Constructor to create an instance of DeleteAccountLogic.
    * @param d The IView given.
    * @param r The IServerRequest given
    */


    public DeleteAccountLogic(IView d,IServerRequest r){
        this.d = d;
        this.r = r;
        r.addIVolleyListener(this);
    }
    
      /**
    * Method to insert inputted paramters in a user object and send request to the server.
    * @param username username
    * @param password password
    * @return Nothing.
    */


    public void deleteUser(String username, String password) {
        String url = "http://coms-309-vb-05.cs.iastate.edu:8080/Users/Delete/" + username;
        JSONObject user_object = new JSONObject();

        try {
            user_object.put("username", username);
            user_object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        r.sendToServer(url, user_object, "POST");

    }

 /**
    * Method to check the inputted paramters if the current password is correct.
    * @param first Current password entered
    * @param second Current password entered second time
    * @param password Current password 
    * @return Response message.
    */



    public String checkInput(String first, String second, String password){


        if(first.equals(second) && first.equals(password)){
            d.toastText("You have successfully entered the data!");
            return "You have successfully entered the data!";
        }

        else if(!first.equals(second) && second.equals(password)){
            d.toastText("First entered password does not equal second!");
            return "First entered password does not equal second!";
        }

        else if(!second.equals(first) && first.equals(password)){
            d.toastText("Second entered password does not equal first!");
            return "Second entered password does not equal first!";
        }

        else if(!first.equals(password) || !second.equals(password)){
            d.toastText("You have not entered the correct password!");
            return "You have not entered the correct password!";
        }

        else{
            d.toastText("You have unsuccessfully entered the data!");
            return "You have unsuccessfully entered the data!";
        }

    }
    
    /**
    * Method to control if request was a success.
    * @param getResponseMessage Respsonse Message
    * @return Nothing.
    */



    @Override
    public void onSuccess(String getResponseMessage){
        d.toastText(getResponseMessage);
    }
    
    
         /**
    * Method to control if request was an error.
    * @param error The error given by server
    * @return Nothing.
    */



    @Override
    public void onError(VolleyError error){

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