package com.example.mysqltest.devtools;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Logger {
    private static final String CLIENT_ERROR_PREFIX = "[C ERROR] ";
    private static final String SYSTEM_ERROR_PREFIX = "[S ERROR] ";
    private static final String INFO_PREFIX = "[INFO] ";

    //"Тублеры" вывода лога
    private static final boolean PRINT_CLIENT_ERRORS = true;
    private static final boolean PRINT_SYSTEM_ERRORS = true;
    private static final boolean PRINT_INFO = true;

    //счётчик созданных логов (для удобства понимания на какой "иттерации" произошёл сбой)
    private static int logCNT = 0;

    private final Context context;

    public Logger(Context context){
        this.context = context;
    }

    public void JSONLog(JSONObject JSON){
        try{
            if(!JSON.getBoolean("result")){
                printClientError(JSON.toString());
            } else {
                printInfo(JSON.toString());
            }
        } catch (JSONException e){
            printSystemError("Parse JSON error!");
        }
    }

    public void printClientError(String msg){
        if (PRINT_CLIENT_ERRORS)
            Log.w(CLIENT_ERROR_PREFIX, msg + getPostfix());
    }

    public void printSystemError(String msg){
        if (PRINT_SYSTEM_ERRORS)
            Log.e(SYSTEM_ERROR_PREFIX, msg + getPostfix());
    }

    public void printInfo(String msg){
        if(PRINT_INFO)
            Log.d(INFO_PREFIX, msg + getPostfix());
    }

    private String getPostfix(){
        return "\n from " + context.toString() + " LOG: " + logCNT;
    }
}
