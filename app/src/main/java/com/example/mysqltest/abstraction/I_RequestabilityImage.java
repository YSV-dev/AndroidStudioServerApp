package com.example.mysqltest.abstraction;

import android.graphics.Bitmap;

import org.json.JSONObject;

public interface I_RequestabilityImage extends I_Requestability{
    void onResponse(Bitmap bitmap, String from, String key);
    void onResponseError(String msg, String from, String key);
}
