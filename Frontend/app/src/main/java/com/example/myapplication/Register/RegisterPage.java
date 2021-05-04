package com.example.myapplication.Register;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AppController;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Interface.IView;
import java.io.Serializable;

/**
 *
 * @author amana1
 */
public class RegisterPage extends AppCompatActivity implements Serializable, IView {

    public String FName;
    public String LName;
    public String Email;
    public String Pass;
    public String CPass;
    Button Submit;
    String url = "http://10.24.227.48:8080/CreateUser";

    ServerReq serverRequest = new ServerReq();


    public RegisterLogic logic = new RegisterLogic(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        logic.setServer(serverRequest);
        setContentView(R.layout.activity_register_page2);
        Submit = findViewById(R.id.button);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FName = ((EditText)findViewById(R.id.Name)).getText().toString();
                LName = ((EditText)findViewById(R.id.LastName)).getText().toString();
                Email = ((EditText)findViewById(R.id.Email)).getText().toString();
                Pass = ((EditText)findViewById(R.id.Pass)).getText().toString();
                CPass = ((EditText)findViewById(R.id.CPass)).getText().toString();

                try {
                    logic.registerUser(url,FName,LName,Email,Pass,CPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void showText(String s) {
        Log.d("Response: ", s);
    }
    @Override
    public void toastText(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void setFocus(int a) {
        findViewById(a).requestFocus();
    }

    public void setServer(ServerReq serverRequest)
    {
        this.serverRequest = serverRequest;
    }
    public void setLogic(RegisterLogic logic)
    {
        this.logic = logic;
    }
}








//
//
//
//  package com.example.myapplication.Register;
//
//  import androidx.appcompat.app.AppCompatActivity;
//
//  import android.content.Intent;
//  import android.os.Bundle;
//  import android.util.Log;
//  import android.view.View;
//  import android.widget.Button;
//  import android.widget.EditText;
//  import android.widget.Toast;
//
//  import com.android.volley.Request;
//  import com.android.volley.RequestQueue;
//  import com.android.volley.Response;
//  import com.android.volley.VolleyError;
//  import com.android.volley.toolbox.JsonObjectRequest;
//  import com.android.volley.toolbox.Volley;
//  import com.example.myapplication.MainActivity;
//  import com.example.myapplication.R;
//  import com.example.myapplication.userDetails;
//
//  import org.json.JSONException;
//  import org.json.JSONObject;
//
//  import java.io.Serializable;
//  import java.sql.Date;
//
//  public class RegisterPage extends AppCompatActivity implements Serializable {
//
//      String FName;
//      String LName;
//      String Email;
//      String Pass;
//      String CPass;
//      Button Submit;
//      userDetails user;
//
//      @Override
//      protected void onCreate(Bundle savedInstanceState) {
//
//
//          super.onCreate(savedInstanceState);
//          setContentView(R.layout.activity_register_page2);
//
//
//          Submit = findViewById(R.id.button);
//          Submit.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  FName = ((EditText)findViewById(R.id.Name)).getText().toString();
//                  LName = ((EditText)findViewById(R.id.LastName)).getText().toString();
//                  Email = ((EditText)findViewById(R.id.Email)).getText().toString();
//                  Pass = ((EditText)findViewById(R.id.Pass)).getText().toString();
//                  CPass = ((EditText)findViewById(R.id.CPass)).getText().toString();
//                  if(CheckEverything())
//                  {// post req
//                      makePostRequest(FName, LName, Email, Pass, CPass);
//                  }
//              }
//          });
//      }
//
//      private void makePostRequest(String fName, String lName, String email, String pass, String cPass) {
//          RequestQueue requestQueue = Volley.newRequestQueue(RegisterPage.this);
//          String url = "http://10.24.227.48:8080/CreateUser";
//          JSONObject user_object = new JSONObject();
//
//          //Get Current Date
//          long millis = System.currentTimeMillis();
//          Date date = new Date(millis);
//
//          //Create Username
//          String username = email.substring(0, email.indexOf('@'));
//
//          try {
//              user_object.put("firstName", fName);
//              user_object.put("lastName", lName);
//              user_object.put("emailAddress", email);
//              user_object.put("userProfilePicture", null);
//              user_object.put("userName", username);
//              user_object.put("userPassword", pass);
//              user_object.put("dateCreated", date);
//
//          }catch (JSONException e){
//              e.printStackTrace();
//          }
//
//          JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, user_object, new Response.Listener<JSONObject>() {
//              @Override
//             public void onResponse(JSONObject response) {
//                  Log.d("Response: ", String.valueOf(response));
//                  FirstPage();
//              }
//         }, new Response.ErrorListener() {
//              @Override
//              public void onErrorResponse(VolleyError error) {
//                  // error
//                  Log.d("Error.Response", String.valueOf(error));
//                  Toast.makeText(getApplicationContext(),"This email address is already in use!",Toast.LENGTH_SHORT).show();
//
//              }
//         });
//          requestQueue.add(objectRequest);
//      }
//
//
//      static boolean isValid(String email) {
//          String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@iastate.edu";
//          return email.matches(regex);
//      }
//      static boolean isValid2(String pass) {
//         String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
//          return pass.matches(regex);
//      }
//      public void FirstPage(){
//         Intent intent = new Intent(this, MainActivity.class);
//          startActivity(intent);
//      }
//      boolean CheckEverything()
//      {
//          if(FName.equals(""))
//          {
//              findViewById(R.id.Name).requestFocus();
//              Toast.makeText(getApplicationContext(),"Please enter your first name",Toast.LENGTH_SHORT).show();
//          }else if(LName.equals("")){
//              findViewById(R.id.Name).requestFocus();
//              Toast.makeText(getApplicationContext(),"Please enter your last name",Toast.LENGTH_SHORT).show();
//      }else if(!isValid(Email))
//          {
//              findViewById(R.id.Email).requestFocus();
//              Toast.makeText(getApplicationContext(),"Please enter a valid ISU email",Toast.LENGTH_SHORT).show();
//
//          }
//          else if(!isValid2(Pass)){
//              String msg = "Please enter a password that satisfies followings conditions:\n"+
//                      "It contains at least 8 characters and at most 20 characters.\n" +
//                      "It contains at least one digit.\n" +
//                      "It contains at least one upper case alphabet.\n" +
//                      "It contains at least one lower case alphabet.\n";
//              findViewById(R.id.Pass).requestFocus();
//              Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
//
//          }
//         else if(!Pass.equals(CPass))
//         {
//              findViewById(R.id.CPass).requestFocus();
//              Toast.makeText(getApplicationContext(),"Passwords Do not match",Toast.LENGTH_SHORT).show();
//          }else
//          {
//              return true;
//          }
//          return false ;
//      }
//  }