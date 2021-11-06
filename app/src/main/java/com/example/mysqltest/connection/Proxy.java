package com.example.mysqltest.connection;

import android.content.Context;
import android.graphics.Bitmap;

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
import com.example.mysqltest.devtools.LoggerErrors;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//простой коннектор к серверу (в данном случае локальному)
public class Proxy {
    private static final String ROOT_URL = "http://192.168.100.2:8080";
    private final String to;
    private final String key;
    private final Context context;
    private final I_Requestability activity;
    private final Map<String, String> data;
    private final Logger log;

    //при отправке из активити
    public Proxy(Context activity, String to, String key, Map<String, String> data){
        this.activity = (I_Requestability)activity;
        this.context = activity;
        this.to = to;
        this.key = key;
        this.data = data;
        this.log = new Logger(context);
    }

    //при отправке из какого либо ещё источника, например из адаптера
    public Proxy(Context activity, I_Requestability req, String to, String key, Map<String, String> data){
        this.activity = req;
        this.context = activity;
        this.to = to;
        this.key = key;
        this.data = data;
        this.log = new Logger(context);
    }

    //отправка JSON запроса ()
    public void sendJSONRequest(){
        RequestQueue reqQueue = Volley.newRequestQueue(context);
        I_RequestabilityJSON req;

        try {
            req = (I_RequestabilityJSON)activity;
        } catch (ClassCastException e) {
            log.printSystemError("Источник не наследует интерфейс передачи JSON данных.");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL+to, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    log.JSONLog(jObject);
                    req.onResponse(jObject, to, key);
                } catch (JSONException e) {
                    log.printSystemError(LoggerErrors.JSON_PARSE_ERROR);
                    req.onResponseError("[Ошибка] Некорректные данные!", to, key);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                req.onResponseError("[Ошибка] Отправки запроса!", to, key);
                log.printSystemError("Ошибка отправки запроса " + error);
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

        ImageRequest imageRequest = new ImageRequest(
                ROOT_URL+to, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                log.printInfo(ROOT_URL+to + " loaded!");
                try {
                    ((I_RequestabilityImage)activity).onResponse(response, to, key);
                } catch(ClassCastException ignored){
                    log.printSystemError("Источник не наследует интерфейс JSON передачи.");
                }
            }
        }, 2048,
                2048, ScaleType.CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        req.onResponseError("[Ошибка] Отправки запроса!", to, key);
                        log.printSystemError("Ошибка отправки запроса " + error);
                    }
        });

        reqQueue.add(imageRequest);
    }
}
