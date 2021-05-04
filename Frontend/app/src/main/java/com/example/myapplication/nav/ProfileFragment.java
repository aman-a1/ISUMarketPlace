package com.example.myapplication.nav;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.EditPro;
import com.example.myapplication.R;
import com.example.myapplication.userDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
* This fragment is the profile page. It displays the first name, last name and profile image. 
* @author Abdula Eljaam (abeljaam)
*/


public class ProfileFragment extends Fragment {
    ImageView profileImage;
    private RequestQueue req;
    TextView userName;
    Button btn;
    userDetails user;
    TextView date;
    Bitmap bitmap = null;
    String url = "http://10.24.227.48:8080/Users/";

  /**
    * Method used to create activity of the profile page and display the image, first name and last name.
    * @param inflater Layout inflater given
    * @param container View Group given
    * @param savedInstanceState Bundle given
    * @return View.
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile, container, false);
        btn = (Button) v.findViewById(R.id.button123);
        date = v.findViewById(R.id.date);
        profileImage = v.findViewById(R.id.profileImage);
        userName = v.findViewById(R.id.userName);
        user = (userDetails) getArguments().getSerializable("user");
        req = Volley.newRequestQueue(this.getActivity());
        makePostRequest(user.username);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               Intent myInt = new Intent(getActivity(), EditPro.class);
                myInt.putExtra("user", (Serializable) user);
                startActivity(myInt);
            }
        });
        return v;
    }
    
    
      /**
    * Method used to make a get request to get the first name, last name and profile image.
    * @param username username
    * @return View.
    */

    private void makePostRequest(String username) {


        JSONObject user_object = new JSONObject();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url+username, user_object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    user.fname = response.getString("firstName");
                    user.lname = response.getString("lastName");
                    user.emailAddress = response.getString("emailAddress");
                    user.username = response.getString("userName");
                    user.date =response.getString("dateCreated");
                    JSONObject object = response.getJSONObject("profilePicture");
                    String s = object.getString("imageString");
                    bitmap = base64ToBitmap(s);
                    if(bitmap != null)
                    {
                        profileImage.setImageBitmap(bitmap);
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                       Toast.makeText(getContext(),"Error while refreshing values!",Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        userName.setText(user.fname+" "+user.lname);
        date.setText("Account created on : "+user.date);

        req.add(objectRequest);
    }
    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }


}