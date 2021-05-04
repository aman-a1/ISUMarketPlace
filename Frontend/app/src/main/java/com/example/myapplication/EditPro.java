package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;


/**
* This activity is to edit the first name and last name of your account and upload an image to your account.
* @author Abdula Eljaam (abeljaam)
*/


public class EditPro extends AppCompatActivity {

    Button submit;
    EditText fname;
    EditText lname;
    ImageButton imageButton;
    userDetails d;
    Uri imageUri;
    ImageView imgP;
    private static final int Pick_Image = 1;
    private Bitmap bitmap;

   /**
    * Method used to create activity, and display two textboxes to enter first and last name.
    * @param savedInstanceState
    * @return Nothing.
    */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pro);
        submit = (Button) findViewById(R.id.submit);

        imgP = findViewById(R.id.profileImage);
        imageButton = findViewById(R.id.updateImg);
        Intent i = getIntent();
        d = (userDetails)i.getSerializableExtra("user");

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePhoto();
            }
        });

        fname = findViewById(R.id.editFirst);
        lname = findViewById(R.id.editLast);

        fname.setHint(d.fname);
        lname.setHint(d.lname);
        if(d.image!=null) {
            imgP = findViewById(R.id.profileImage);
            imgP.setImageBitmap(d.image);
        }

        submit.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String f = fname.getText().toString();
                String l = lname.getText().toString();
                makePostRequest(f,l,bitmap,d.username);
                Toast.makeText(getApplicationContext(),"Your details have been updated successfully!",Toast.LENGTH_SHORT).show();
                d.fname = f;
                d.lname = l;
                d.image = bitmap;
            }
        });


    }
    
       /**
    * Method used to update your profile image.
    * @return Nothing.
    */

    private void updatePhoto() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Please Select a image!"), Pick_Image);
    }

    /**
    * On click method for the back button.
    * @param v View given
    * @return Nothing.
    */

    public void onClick(View v) {
        Intent myInt = new Intent(EditPro.this , AfterLogin.class);
        myInt.putExtra("user",(Serializable)d);
        startActivity(myInt);
    }


    /**
    * Method that sends a post request to the server to update profile.
    * @param fName first name
    * @param lName last name
    * @param bitmap bitmap
    * @param username username
    * @return Nothing.
    */


    private void makePostRequest(String fName, String lName, Bitmap bitmap, String username) {
        RequestQueue requestQueue = Volley.newRequestQueue(EditPro.this);
        String url = "http://10.24.227.48:8080/User/"+username;
        String imageFile = bitmapToBase64(bitmap);
        JSONObject user_object = new JSONObject();

        try {
            user_object.put("firstName", fName);
            user_object.put("lastName", lName);
            user_object.put("imageName","profile");
            user_object.put("imageType","jpeg");
            user_object.put("imageString",imageFile);

        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, user_object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("cResponse: ", String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error.toString());
                Log.d("Error.Response", String.valueOf(error));
            }
        });
        requestQueue.add(objectRequest);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Pick_Image && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imgP.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * converts bitmap to base 64
     * @param bitmap
     * @return
     */
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}

/*



    private void makePutRequest(String fName, String lName,String username) {
        RequestQueue requestQueue = Volley.newRequestQueue(EditPro.this);
        String url = "http://coms-309-vb-05.cs.iastate.edu:8080/User/"+username;

        JSONObject user_object = new JSONObject();

        try {
            user_object.put("firstName", fName);
            user_object.put("lastName", lName);
            user_object.put("emailAddress", null);
            user_object.put("userName", null);
            user_object.put("userPassword", null);
            user_object.put("userRating", null);
            user_object.put("dateCreated", null);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, user_object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", String.valueOf(response));

                //On Success
                String getResponseMessage;

                try {
                    getResponseMessage = response.getString("message");
                    Toast.makeText(getApplicationContext(), getResponseMessage,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                String body;
                String getResponseMessage;
                JSONObject bodyJsonObject;
                String statusCode = String.valueOf(error.networkResponse.statusCode);

                if(statusCode.equals("406")){
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
        requestQueue.add(objectRequest);
    }

}

 */