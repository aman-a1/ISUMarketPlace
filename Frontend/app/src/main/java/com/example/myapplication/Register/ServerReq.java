package com.example.myapplication.Register;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.example.myapplication.AppController;
import com.example.myapplication.Interface.IServerReq;
import com.example.myapplication.Interface.IVolleyListener;


import org.json.JSONObject;

public class ServerReq implements IServerReq {

    private String tag_json_obj = "json_obj_req";
    private IVolleyListener l;

    @Override
    public void sendToServer(String url, JSONObject newUserObj, String methodType) {


        int method = Request.Method.GET;
        if (methodType.equals("POST")) {method = Request.Method.POST;}

        JsonObjectRequest objectRequest = new JsonObjectRequest(method, url, newUserObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                l.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                l.onError(error.toString());
            }

        });

        AppController.getInstance().addToRequestQueue(objectRequest, tag_json_obj);
    }

    public void addVolleyListener(IVolleyListener l) {
        this.l = l;
    }
}
