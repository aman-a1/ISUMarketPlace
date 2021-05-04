package com.example.myapplication;


/**
* This activity is the request to change the password of your account. You will be prompted to enter the current password of your account, and then enter the new password and hit submit.
* @author Abdula Eljaam (abeljaam)
*/


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SecurityRequest implements IServerRequest{

    private IVolleyListener l;
    private String tag_json_obj = "json_obj_request";
    
    
       /**
    * Method used to send a put request to the server to change the user's password.
    * @param url Url of the server
    * @param user_object JSON object that has the parameters
    * @param methodType What type of request 
    * @return Nothing.
    */


    @Override
    public void sendToServer(String url, JSONObject user_object, String methodtype){

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, user_object, new Response.Listener<JSONObject>() {

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
