package com.example.mysqltest.connection;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.widget.ImageView.ScaleType;
import com.example.mysqltest.abstraction.I_Requestability;
import com.example.mysqltest.abstraction.I_RequestabilityImage;
import com.example.mysqltest.devtools.Logger;
import com.example.mysqltest.abstraction.I_RequestabilityJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Proxy {
    private static final String ROOT_URL = "http://192.168.100.2:8080";
    private String to;
    private String key;
    private Context context;
    private I_Requestability activity;
    private Map<String, String> data;

    public Proxy(AppCompatActivity activity, String to, String key, Map<String, String> data){
        if( !(activity instanceof I_RequestabilityJSON) ){
            throw new IllegalArgumentException();
        }
        this.activity = (I_Requestability)activity;
        this.context = activity;
        this.to = to;
        this.key = key;
        this.data = data;
    }

    public void sendJSONRequest(){
        RequestQueue reqQueue = Volley.newRequestQueue(context);
        I_RequestabilityJSON req;

        try {
            req = (I_RequestabilityJSON)activity;
        } catch (ClassCastException e) {
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL+to, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    new Logger(context).JSONLog(jObject);
                    req.onResponse(jObject, to, key);
                } catch (JSONException e) {
                    new Logger(context).printSystemError("Incorrect data");
                    req.onResponseError("[Ошибка] Некорректные данные!", to, key);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                req.onResponseError("[Ошибка] Отправки запроса!", to, key);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = data;
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        reqQueue.add(stringRequest);
    }

    public void sendImageRequest(){
        RequestQueue reqQueue = Volley.newRequestQueue(context);
        I_RequestabilityImage req = null;

        ImageRequest imageRequest = new ImageRequest(
                ROOT_URL+to, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                new Logger(context).printInfo(ROOT_URL+to + " loaded!");
                try {
                    ((I_RequestabilityImage)activity).onResponse(response, to, key);
                } catch(ClassCastException e){

                }
            }
        }, 2048,
                2048, ScaleType.CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        req.onResponseError("[Ошибка] Отправки запроса!", to, key);
                    }
        });

        reqQueue.add(imageRequest);
    }
}
