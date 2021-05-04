package com.example.myapplication.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.AfterLogin;
import com.example.myapplication.AppController;
import com.example.myapplication.R;
import com.example.myapplication.Register.ServerReq;
import com.example.myapplication.Interface.IView;
import com.example.myapplication.userDetails;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Aman Agarwal
 */
public class AddNewItem extends AppCompatActivity implements IView {

    ImageView IDProf;
    Button Upload_Btn;
    private String Document_img1="";
    Uri imageUri;
    userDetails d;
    Bitmap bitmap;
    EditText title;
    EditText des;
    EditText price;

    ServerReq serverRequest = new ServerReq();

    public AddNewProductLogic logic = new AddNewProductLogic(this);
    private static final int Pick_Image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        logic.setServer(serverRequest);
        setContentView(R.layout.activity_add_new_item);
        IDProf = findViewById(R.id.IdProfADD);
        Upload_Btn = findViewById(R.id.submitADD);
        Intent i = getIntent();
        d = (userDetails)i.getSerializableExtra("user");
        final String url = "http://10.24.227.48:8080/users/"+d.username+"/products";
        IDProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        Upload_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title = findViewById(R.id.titleADD);
                des = findViewById(R.id.desADD);
                price = findViewById(R.id.priceADD);
                logic.Add(title.getText().toString(),des.getText().toString(),price.getText().toString(),bitmap,url);
            }
        });

    }
/*
    Selects the image from the user's device
 */
    private void SelectImage() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Please Select a image!"), Pick_Image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Pick_Image && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                IDProf.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    /**
     * Helper function used to display text
    */
    @Override
    public void showText(String s) {
        Log.i("message",s);
    }
    /**
    * Helper function used to make a toast
    */
    @Override
    public void toastText(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     */
    @Override
    public void startActivity() {
        Intent intent = new Intent(AddNewItem.this , AfterLogin.class);
        intent.putExtra("user",(Serializable)d);
        intent.putExtra("FragmentName","Home");
        startActivity(intent);
    }

    /**
     * Sets the focus for the given param
     * @param a
     */
    @Override
    public void setFocus(int a) {
        findViewById(a).requestFocus();
    }
}









/*
package com.example.myapplication.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AfterLogin;
import com.example.myapplication.EditPro;
import com.example.myapplication.R;
import com.example.myapplication.nav.HomeFragment;
import com.example.myapplication.nav.ProfileFragment;
import com.example.myapplication.userDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;


public class AddNewItem extends AppCompatActivity {

    ImageView IDProf;
    Button Upload_Btn;
    private String Document_img1="";
    Uri imageUri;
    userDetails d;
    Bitmap bitmap;
    EditText title;
    EditText des;
    EditText price;
    private static final int Pick_Image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        IDProf = (ImageView) findViewById(R.id.IdProf);
        Upload_Btn = (Button) findViewById(R.id.submit);
        Intent i = getIntent();
        d = (userDetails)i.getSerializableExtra("user");

        IDProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        Upload_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title = findViewById(R.id.tile);
                des = findViewById(R.id.des);
                price = findViewById(R.id.price);

                if (CheckEverything()) {
                    makePostReq();
                    GoBack();
                }
            }
        });

    }
    boolean CheckEverything()
    {
        if(title.getText().toString().equals(""))
        {
            findViewById(R.id.tile).requestFocus();
            Toast.makeText(getApplicationContext(),"Please enter a title for your item!",Toast.LENGTH_SHORT).show();
        }else if(des.getText().toString().equals("")){
            findViewById(R.id.des).requestFocus();
            Toast.makeText(getApplicationContext(),"Please enter a description for your item!",Toast.LENGTH_SHORT).show();
        }
        else if(price.getText().toString().equals("")){
            findViewById(R.id.price).requestFocus();
            Toast.makeText(getApplicationContext(),"Please enter a price for your item!",Toast.LENGTH_LONG).show();

        }
        else if(bitmap == null)
        {
            findViewById(R.id.IdProf).requestFocus();
            Toast.makeText(getApplicationContext(),"Please select a image of your item!",Toast.LENGTH_SHORT).show();
        }else
        {
            return true;
        }
        return false ;
    }

    private void SelectImage() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Please Select a image!"), Pick_Image);
    }

    private void GoBack() {
        Intent intent = new Intent(AddNewItem.this , AfterLogin.class);
        intent.putExtra("user",(Serializable)d);
        intent.putExtra("FragmentName","Home");
        startActivity(intent);
    }
    private void makePostReq() {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(AddNewItem.this);
            String url = "http://10.24.227.48:8080/users/"+d.username+"/products";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            String imageFile = bitmap.toString();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);
            JSONObject user_object = new JSONObject();
            try {
                user_object.put( "productName",title.getText());
                user_object.put( "productDescription",des.getText());
                user_object.put( "productPrice",Double.parseDouble(String.valueOf(price.getText())));
                user_object.put( "productQuantity",1);
                user_object.put("datePosted", dateString);
                user_object.put("productStatus", true);
                user_object.put("productCategory", "ALL");

                JSONObject image = new JSONObject();
                image.put("imageName","product");
                image.put("imageType","jpeg");
                image.put("imageString",imageFile);
                JSONArray arr = new JSONArray();
                arr.put(image);
                user_object.put("productImages",arr);

            }catch (JSONException e){
                e.printStackTrace();
            }


            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, user_object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response: ", String.valueOf(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("Error.Response",error.toString());
                    Toast.makeText(getApplicationContext(),"Your item has been added!",Toast.LENGTH_SHORT);
                }
            });
             requestQueue.add(objectRequest);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Pick_Image && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                IDProf.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
 */