package com.example.myapplication;

/**
* This activity is to change the password of your account. You will be prompted to enter the current password of your account, and then enter the new password and hit submit.
* @author Abdula Eljaam (abeljaam)
*/



import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class Security extends AppCompatActivity implements IView{

    Button submit;
    String password;
    String current;
    userDetails d;
    EditText t;
    EditText cur;
    Button show;
    int i;
    
       /**
    * Method used to create activity, and display two textboxes to enter current and new password. 
    * @param savedInstanceState
    * @return Nothing.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        t = findViewById(R.id.editpass);
        cur = findViewById(R.id.entercur);

        cur.setTransformationMethod(new PasswordTransformationMethod());
        t.setTransformationMethod(new PasswordTransformationMethod());

        submit = findViewById(R.id.submitbutton2);
        show = findViewById(R.id.show);

        i = 0;

        SecurityRequest r = new SecurityRequest();
        final SecurityLogic l = new SecurityLogic(this,r);

        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                l.showHide(i, show, t);
                i++;
            }
        });

        Intent i = getIntent();
        d = (userDetails)i.getSerializableExtra("user");


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                password = ((EditText)findViewById(R.id.editpass)).getText().toString();
                current =  ((EditText)findViewById(R.id.entercur)).getText().toString();

                String ret = l.checkInputChange(current, password, d.password);

                if(ret.equals("You have successfully entered the data!")){
                    l.changePassword(d.username, d.password, password);
                }

            }
        });


    }

    /**
    * Method used to show text on the activity.
    * @param s String you want displayed.
    * @return Nothing.
    */

    @Override
    public void showText(String s){
    }
    
     /**
    * Method used to toast text on the activity.
    * @param s String you want displayed.
    * @return Nothing.
    */

    @Override
    public void toastText(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}
