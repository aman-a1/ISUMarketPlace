package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Moderator.home;
import com.example.myapplication.Register.RegisterPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @author amana1
 * Log's in the user, if coorect credentials are provided
 */
public class MainActivity extends AppCompatActivity {

    private RequestQueue req;
    String url = "http://10.24.227.48:8080/AuthenticateLogin";

    Button btn;
    Button btn1;
    Boolean access = false;
    static final String Tag = "TestApp";
    public static String Name = " ";
    public static String Password = " ";
    userDetails user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.reg);
        btn1 = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterPage();
            }
        });
        req = Volley.newRequestQueue(this);
        findViewById(R.id.forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = ((EditText)findViewById(R.id.userName)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();

                if(username.equals("")||password.equals("")) {
                    if (username.equals("")) {
                        ((EditText) findViewById(R.id.userName)).requestFocus();
                        Toast.makeText(getApplicationContext(),"Please enter your ISU username",Toast.LENGTH_SHORT).show();
                    }
                    if (password.equals("")) {
                        ((EditText) findViewById(R.id.password)).requestFocus();
                        Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(username.equals("private12345mod") && password.equals("private12345mod"))
                {
                    moderator();
                }
                else {
                    makePostRequest(username,password);
                }
            }


        });

    }

    /**
     * opens the moderator account
     */
    private void moderator() {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    /**
     * opens the registration page side
     */

    private void RegisterPage() {
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }

    public void FirstPage(){
        Intent intent = new Intent(this, AfterLogin.class);
        intent.putExtra("user", (Serializable) user);
        intent.putExtra("FragmentName","Home");
        startActivity(intent);
    }

    public void forgot(){
        Intent intent = new Intent(this, forgot_Password.class);
        startActivity(intent);
    }

    /**
     * makes request to the server to see if correct username and password if provided.
     * @param username
     * @param password
     */
    private void makePostRequest(String username, final String password) {

        JSONObject user_object = new JSONObject();
        try {

            user_object.put("username",username);
            user_object.put("password", password);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, user_object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String fname = response.getString("firstName");
                    String lname = response.getString("lastName");
                    String emailAddrs = response.getString("emailAddress");
                    String usrname = response.getString("userName");
                    String dateCreated =response.getString("dateCreated");
                    user = new userDetails(fname, lname, usrname, password, emailAddrs, dateCreated);

                }
                catch (Exception e) {
                    e.printStackTrace();
                    //    Toast.makeText(getApplicationContext(),"Please enter a correct email-password combination!",Toast.LENGTH_SHORT).show();
                }
                FirstPage();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
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
                            Toast.makeText(getApplicationContext(), getResponseMessage,Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Log.d("Error.Response", String.valueOf(error));
                }
            }
        });
        req.add(objectRequest);
    }



}
/*
      // error

 */
