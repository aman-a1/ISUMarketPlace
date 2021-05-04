package com.example.myapplication;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
* Interface for Server Request
* @author Abdula Eljaam (abeljaam)
*/


interface IServerRequest{
    public void sendToServer(String url, JSONObject user_object, String methodtype);
    public void addIVolleyListener(IVolleyListener logic);
}


/**
* This is the class that holds the server request to delete your account.
* @author Abdula Eljaam (abeljaam)
*/


public class DeleteAccountRequest implements IServerRequest{

    private IVolleyListener l;
    private String tag_json_obj = "json_obj_request";



  /**
    * Method used to send a put request to the server to delete the user's account.
    * @param url Url of the server
    * @param user_object JSON object that has the parameters
    * @param methodType What type of request 
    * @return Nothing.
    */


    @Override
    public void sendToServer(String url, JSONObject user_object, String methodtype){

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, user_object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", String.valueOf(response));

                String getResponseMessage;

                try {
                    getResponseMessage = response.getString("message");
                    l.onSuccess(getResponseMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                l.onError(error);
            }
        });

        AppController.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }
    
     
       /**
    * Method used to set IVolleyListener.
    * @param logic IVolleyListener given
    * @return Nothing.
    */

    @Override
    public void addIVolleyListener(IVolleyListener logic){
        l = logic;
    }


}


