package com.example.myapplication.Register;

import android.util.Log;

import com.example.myapplication.R;

import com.example.myapplication.Interface.IServerReq;
import com.example.myapplication.Interface.IView;
import com.example.myapplication.Interface.IVolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

/**
 * @author amana1
 */

public class RegisterLogic implements IVolleyListener {
   public IView r;
    IServerReq serverReq;
    String FName;
    String LName;
    String Email;
    String Pass;
    String CPass;
    String url;
    public RegisterLogic(IView r)
    {
        this.r = r;
    }
    public void registerUser(  String url,String FName,
            String LName,
            String Email,
            String Pass,
            String CPass)  {
        this.url = url;
        this.FName = FName;
        this.LName = LName;
        this.Email = Email;
        this.Pass = Pass;
        this.CPass = CPass;
        if(CheckEverything())
        {
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            String username = Email.substring(0, Email.indexOf('@'));
            JSONObject user_object = new JSONObject();
            try {
                user_object.put("firstName", FName);
                user_object.put("lastName", LName);
                user_object.put("emailAddress", Email);
                user_object.put("userProfilePicture", null);
                user_object.put("userName", username);
                user_object.put("userPassword", Pass);
                user_object.put("dateCreated", date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            serverReq.sendToServer(url,user_object,"POST");
        }

    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@iastate.edu";
        return email.matches(regex);
    }
    static boolean isValid2(String pass) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        return pass.matches(regex);
    }
    boolean CheckEverything() {
        if(FName.equals(""))
        {
            r.setFocus(R.id.Name);
            r.toastText("Please enter your first name");
        }else if(LName.equals("")){
            r.setFocus(R.id.Name);
            r.toastText("Please enter your last name");

        }else if(!isValid(Email))
        {
            r.setFocus(R.id.Email);
            r.toastText("Please enter a valid ISU email");
        }
        else if(!isValid2(Pass)){
            String msg = "Please enter a password that satisfies followings conditions:\n"+
                    "It contains at least 8 characters and at most 20 characters.\n" +
                    "It contains at least one digit.\n" +
                    "It contains at least one upper case alphabet.\n" +
                    "It contains at least one lower case alphabet.\n";
            r.setFocus(R.id.Pass);
            r.toastText(msg);
        }
        else if(!Pass.equals(CPass))
        {
            r.setFocus(R.id.CPass);
            r.toastText("Passwords Do not match");
        }else
        {
            return true;
        }
    return  false;
    }
    @Override
    public void onSuccess(String s) {

        r.showText(s);
        r.startActivity();
    }
    public void setServer(IServerReq serverReq)
    {
        this.serverReq = serverReq;
        serverReq.addVolleyListener(this);
    }
    @Override
    public void onError(String s) {
        // error
        Log.d("Error.Response", s);
        r.showText(s);
        r.toastText("This email address is already in use!");
    }
}
