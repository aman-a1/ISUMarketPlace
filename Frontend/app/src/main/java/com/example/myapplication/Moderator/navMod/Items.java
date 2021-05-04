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
import android.widget.GridView;
import android.widget.ImageView;
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
import com.example.myapplication.R;
import com.example.myapplication.home.All;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

public class Items extends Fragment {
    GridView gridView;
    String[] title = {};
    int[] price = {};
    String[] des = {};
    String[] image = {};

    View v;
    String url = "http://10.24.227.48:8080/GetAllProducts";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.item, container, false);

        makePostRequest();
        gridView = v.findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductInfo();
            }
        });
        return v;
    }

    private void ProductInfo() {
        Intent intent = new Intent(this.getContext(), product_info.class);
        intent.putExtra("username","username");
        intent.putExtra("parent","items");
        startActivity(intent);

    }

    private void makePostRequest() {
        RequestQueue req =  Volley.newRequestQueue(getActivity());
        JSONArray usr_obj = new JSONArray();
        JsonArrayRequest objReq = new JsonArrayRequest(Request.Method.GET, url, usr_obj, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                title = new String[response.length()];
                price = new int[response.length()];
                des = new String[response.length()];
                image = new String[response.length()];

                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        title[i] = (obj.getString("productName"));
                        price[i] = (int) obj.getDouble("productPrice");
                        des[i] = obj.getString("productDescription");
                        JSONArray a = obj.getJSONArray("productImages");
                        image[i] = (a.getJSONObject(0).getString("imageString"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                MyAdapter adapter = new MyAdapter(getContext(),title,price, des,image);
                gridView.setAdapter(adapter);

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
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
}
