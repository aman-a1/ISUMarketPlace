package com.example.myapplication.Moderator.navMod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DeleteAccount;
import com.example.myapplication.Interface.IVolleyListener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Moderator.home;
import com.example.myapplication.R;
import com.example.myapplication.Register.ServerReq;
import com.example.myapplication.userDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountInfo extends AppCompatActivity {

    GridView gridView;
   static String title[] = {};
  static   String des[] = {};
  static   String image[] = {};
  static   int price[] = {};
  static   int ID[] = {};
  static  int length = 0;
  userDetails user;
    String url = "http://10.24.227.48:8080/Users/";
    TextView name;
    TextView date;
    ImageView profile;
    Button del;
    TextView email;


    /**
     * Displays the account information
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        gridView = findViewById(R.id.grid_view);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        email = findViewById(R.id.email);
        del = findViewById(R.id.deleteAccount);
        profile = findViewById(R.id.profileImage);
        Intent intent = getIntent();
        String username =  intent.getStringExtra("username");
        makePostRequest(username);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT);
                productinfo(ID[position]);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if((one.getText().toString().equals(two.getText().toString()))){



                    AlertDialog.Builder b = new AlertDialog.Builder(AccountInfo.this);
                    b.setMessage("Are you sure you want to permanently delete your ISU  Marketplace " +
                            "account? You will not be able to restore your account or account content and all " +
                            "your current postings will be removed.");
                    b.setTitle("Confirmation of account deletion");

                    b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteUser(user.username, user.password);
                            Intent intent = new Intent(AccountInfo.this, home.class);
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


        });
    }

    private void deleteUser(String username, String password) {


    }
    private void productinfo(int id) {
        Intent intent = new Intent(this.getApplicationContext(), product_info.class);

        intent.putExtra("productID",id);
        intent.putExtra("username",user.username);

        startActivity(intent);
    }


    /**
     * Retrives data from the server for the provided username
     * @param username
     */

    private void makePostRequest(final String username) {
        RequestQueue req =  Volley.newRequestQueue(this);
        JSONObject user_object = new JSONObject();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url+username, user_object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {


                    String fname = response.getString("firstName");
                    String lname = response.getString("lastName");
                    String emailAddrs = response.getString("emailAddress");
                    String dateCreated =response.getString("dateCreated");
                    String password =response.getString("userPassword");
                    user = new userDetails(fname, lname, username, password, emailAddrs, dateCreated);


                    JSONObject object = response.getJSONObject("profilePicture");
                    String s = object.getString("imageString");
                    user.image = base64ToBitmap(s);
                    if(user.image != null)
                    {
                        profile.setImageBitmap(user.image);
                    }
                    JSONArray arr =  response.getJSONArray("userProducts");

                   length = arr.length();
                   title = new String[length];
                   price = new int[length];
                   image = new String[length];
                   des = new String[length];
                   ID = new int[length];
                    for(int i=0;i<length;i++)
                    {
                        try {
                            JSONObject obj = arr.getJSONObject(i);
                            title[i] = (obj.getString("productName"));
                            ID[i] = obj.getInt("id");
                            price[i] = (int) obj.getDouble("productPrice");
                            des[i] = obj.getString("productDescription");
                            JSONArray a = obj.getJSONArray("productImages");
                            image[i] = (a.getJSONObject(0).getString("imageString"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();

                    //    Toast.makeText(getApplicationContext(),"Please enter a correct email-password combination!",Toast.LENGTH_SHORT).show();
                }
                   MyAdapter adapter = new MyAdapter(getApplicationContext(), title, price,des,image);
                   gridView.setAdapter(adapter);
                   name.setText(user.fname + " " + user.lname);
                   date.setText("Created:"+user.date);
                   email.setText(user.emailAddress);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error

                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        req.add(objectRequest);
    }


    /**
     * displays all the products listed by the user
     */
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String[] title;
        String[] image;
        String[] des;
        int[] price;
        public MyAdapter(Context c, String[] title, int[] price, String[] des, String[] image) {
            super(c,R.layout.item_box,R.id.titleN,title);
            this.context = c;
            this.title = title;
            this.image = image;
            this.des = des;
            this.price = price;

        }
        private Bitmap base64ToBitmap(String b64) {
            byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = layoutInflater.inflate(R.layout.item_box,parent,false);

            ImageView img = row.findViewById(R.id.ImgNt);
            TextView mt = row.findViewById(R.id.title);
            img.setImageBitmap(base64ToBitmap(image[position]));

            mt.setText(title[position]);
            TextView mt2= row.findViewById(R.id.priceTag);
            mt2.setText("$ "+price[position]);
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