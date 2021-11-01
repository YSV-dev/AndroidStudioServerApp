package com.example.mysqltest.abstraction;

import org.json.JSONObject;

public interface I_RequestabilityJSON extends I_Requestability{
    void onResponse(JSONObject JSON, String from, String key);
    void onResponseError(String msg, String from, String key);
}
