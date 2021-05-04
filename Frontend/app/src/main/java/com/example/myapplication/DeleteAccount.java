package com.example.myapplication;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

 /**
    * Interface for the view
    */

interface IView{
    public void showText(String s);
    public void toastText(String s);
}



/**
* This activity is to delete your account. You will be prompted to enter the current password of your account twice and then hit submit to confirm deletion.
* @author Abdula Eljaam (abeljaam)
*/


public class DeleteAccount extends AppCompatActivity implements IView{

    Button confirm;
    EditText one;
    EditText two;
    userDetails d;
    
     /**
    * Method used to create activity, and display two textboxes to enter current password twice. 
    * @param savedInstanceState
    * @return Nothing.
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        new AppController();

        confirm = (Button) findViewById(R.id.delete_acc);
        one = (EditText) findViewById(R.id.enterpass);
        two = (EditText) findViewById(R.id.confirmpass);
        one.setTransformationMethod(new PasswordTransformationMethod());
        two.setTransformationMethod(new PasswordTransformationMethod());


        Intent i = getIntent();
        d = (userDetails)i.getSerializableExtra("user");

        DeleteAccountRequest r = new DeleteAccountRequest();
        final DeleteAccountLogic l = new DeleteAccountLogic(this,r);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   if((one.getText().toString().equals(two.getText().toString()))){

                String ret = l.checkInput(one.getText().toString(), two.getText().toString(), d.password);

                if(ret.equals("You have successfully entered the data!")){

                    AlertDialog.Builder b = new AlertDialog.Builder(DeleteAccount.this);
                    b.setMessage("Are you sure you want to permanently delete your ISU  Marketplace " +
                            "account? You will not be able to restore your account or account content and all " +
                            "your current postings will be removed.");
                    b.setTitle("Confirmation of account deletion");

                    b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            l.deleteUser(d.username, d.password);
                            Intent intent = new Intent(DeleteAccount.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alert = b.create();
                    alert.show();

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