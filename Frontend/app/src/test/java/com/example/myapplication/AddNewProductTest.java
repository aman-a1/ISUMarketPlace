//package com.example.myapplication;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.graphics.Bitmap;
//
//import com.example.myapplication.Register.RegisterPage;
//import com.example.myapplication.Register.ServerReq;
//import com.example.myapplication.home.AddNewItem;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.MockitoRule;
//import org.robolectric.Robolectric;
//
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyObject;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AddNewProductTest {
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Mock
//    ServerReq req;
//
//    @Mock
//    userDetails user;
//    AddNewItem addNewItem;
//
//
//    @Test
//    public void productTestSuccess()
//    {
//        user = new userDetails("fname", "lname", "usrname", "password", "emailAddrs", "dateCreated");
//
//        AddNewItem addNewItem =  Robolectric.buildActivity(AddNewItem.class).create()
//                .resume()
//                .get();
//
//
//        addNewItem.logic.setServer(req);
//        addNewItem.logic.Add("testTitle","test product description","300", (Bitmap) any(),anyString());
//
//        Date date = new Date();
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = sdf.format(date);
//        JSONObject user_object = new JSONObject();
//        try {
//            user_object.put( "productName","testTitle");
//            user_object.put( "productDescription","test product description");
//            user_object.put( "productPrice",Double.parseDouble("300"));
//            user_object.put( "productQuantity",1);
//            user_object.put("datePosted", dateString);
//            user_object.put("productStatus", true);
//            user_object.put("productCategory", "ALL");
//
//            JSONObject image = new JSONObject();
//            image.put("imageName","product");
//            image.put("imageType","jpeg");
//            image.put("imageString",anyString());
//            JSONArray arr = new JSONArray();
//            arr.put(image);
//            user_object.put("productImages",arr);
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        verify(req, times(0)).sendToServer(anyString(), user_object,"POST");
//    }
//
//}
