package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author amana1
 * used in case of forget password
 */
public class forgot_Password extends AppCompatActivity {

    EditText email;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        email = findViewById(R.id.email);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid(email.getText().toString()))
                {
                    submitReq(email.getText().toString());
                }else
                {
                    Toast.makeText(getApplicationContext(),"Please enter a valid ISU email! Thank you",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void submitReq(String toString) {


    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@iastate.edu";
        return email.matches(regex);
    }
}


