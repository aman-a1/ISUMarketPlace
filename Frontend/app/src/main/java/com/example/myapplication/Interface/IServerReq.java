package com.example.myapplication.Interface;

import org.json.JSONObject;

public interface IServerReq {

public void sendToServer(String url, JSONObject newUserObj, String methodType);

public void addVolleyListener(IVolleyListener logic);

}

