package com.example.myapplication.Moderator.navMod;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.example.myapplication.Moderator.navMod.AccountInfo;
import com.example.myapplication.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.transform.ErrorListener;


public class Accounts extends Fragment {
    ListView listView;
   static String first[]={};
   static String[] second={};
   static String[] email= {};
   static String[] time={};
   static Bitmap[] image={};
   int length = 0;

    String url = "http://10.24.227.48:8080/GetAllUser";

    /**
     * Displays all the accounts in the system
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account, container, false);
        makePostRequest();

        listView = v.findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               acc_info(email[position]);
            }
        });
        return v;
    }

    /**
     * starts a new activity to display a specific account info
     * @param s
     */
    private void acc_info(String s) {
        Intent intent = new Intent(this.getContext(), AccountInfo.class);
        intent.putExtra("username",s.substring(0,s.indexOf('@')));
        startActivity(intent);
    }

    /**
     * makes a post req to get info of all the accounts
     */
    private void makePostRequest() {
     RequestQueue req =  Volley.newRequestQueue(getContext());
        JSONArray usr_obj = new JSONArray();
        JsonArrayRequest objReq = new JsonArrayRequest(Request.Method.GET, url, usr_obj, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                length = response.length();
                first = new String[length];
                second = new String[length];
                email = new String[length];
                time = new String[length];
                image = new Bitmap[length];

                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        first[i]=obj.getString("firstName");
                        second[i]=obj.getString("lastName");
                        email[i]=obj.getString("emailAddress");
                        time[i] = obj.getString("dateCreated");
                        JSONObject object = obj.getJSONObject("profilePicture");
                        String s = object.getString("imageString");
                        image[i] = base64ToBitmap(s);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                MyAdapterAccounts adapter = new MyAdapterAccounts(getContext(),first,second,email ,time,image);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("error:",error.toString());
            }
        });

        req.add(objReq);

    }

    /**
     * Displays all the account in a list view
     */
    class MyAdapterAccounts extends ArrayAdapter<String> {


        Context context;
        Bitmap image[];
        String FirstName[];
        String SecondName[];
        String EmailAddress[];
        String time[];


        public MyAdapterAccounts(Context c, String[] F, String[] S, String[] E,String[] time,Bitmap[] image) {
            super(c,R.layout.account_box,R.id.Email,E);
            this.context = c;
            this.image = image;
            this.FirstName = F;
            this.SecondName = S;
            this.EmailAddress = E;
            this.time = time;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = layoutInflater.inflate(R.layout.account_box,parent,false);

            ImageView  img = row.findViewById(R.id.profileImage);
            if(image[position] != null) {
                img.setImageBitmap(image[position]);
            }

            TextView first = row.findViewById(R.id.First);
            first.setText(FirstName[position]);

            TextView Second = row.findViewById(R.id.Second);
            Second.setText(SecondName[position]);



            TextView Time = row.findViewById(R.id.time);
            Time.setText(time[position]);

            return row;
        }
    }
    /**
     * converts the base64 imagefile to a bitmap imagefile
     * @param b64
     * @return
     */
    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}





