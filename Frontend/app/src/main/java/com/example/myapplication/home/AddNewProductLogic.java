package com.example.myapplication.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.Interface.IServerReq;
import com.example.myapplication.Interface.IView;
import com.example.myapplication.Interface.IVolleyListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author amana1
 */
public class AddNewProductLogic implements IVolleyListener {
    IView view;
    private String title;
    private String des;
    private String price;
    private Bitmap bitmap;
    IServerReq serverReq;
    String url;
    public AddNewProductLogic(IView view)
    {
        this.view = view;
    }
    public void setServer(IServerReq iServerReq)
    {
        this.serverReq = iServerReq;
        serverReq.addVolleyListener(this);
    }
    public void Add(String title, String des, String price, Bitmap bitmap, String url)
    {

        this.title = title;
        this.des = des;
        this.price = price;
        this.bitmap = bitmap;
        this.url = url;

        if (CheckEverything())
        {
            String imageFile = bitmapToBase64(bitmap);
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);
            JSONObject user_object = new JSONObject();
            try {
                user_object.put( "productName",title);
                user_object.put( "productDescription",des);
                user_object.put( "productPrice",Double.parseDouble(price));
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
            serverReq.sendToServer(url,user_object,"POST");
        }
    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    boolean CheckEverything()
    {
        if(title.equals(""))
        {
            view.setFocus(R.id.titleADD);
            view.toastText("Please enter a title for your item!");
        }else if(des.equals("")){
            view.setFocus(R.id.desADD);
            view.toastText("Please enter a description for your item!");
        }
        else if(price.equals("")){
            view.setFocus(R.id.priceADD);
            view.toastText("Please enter a price for your item!");

        }
        else if(bitmap == null)
        {
            view.setFocus(R.id.IdProfADD);
            view.toastText("Please select a image for your item!");
        }else
        {
            return true;
        }
        return false ;
    }

    @Override
    public void onSuccess(String s) {
        view.toastText("Your Item has been succesfully added!!");
        view.startActivity();
    }

    @Override
    public void onError(String s) {
        Log.d("error",s);

    }
}
