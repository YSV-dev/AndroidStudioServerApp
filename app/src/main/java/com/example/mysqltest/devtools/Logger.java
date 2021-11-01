package com.example.mysqltest.devtools;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class Logger {

    private static final String CLIENT_ERROR_PREFIX = "[C ERROR] ";
    private static final String SYSTEM_ERROR_PREFIX = "[S ERROR] ";
    private static final String INFO_PREFIX = "[INFO] ";

    private static final boolean PRINT_CLIENT_ERRORS = true;
    private static final boolean PRINT_SYSTEM_ERRORS = true;
    private static final boolean PRINT_INFO = true;

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

    public void printClientError(String JSON){
        if (PRINT_CLIENT_ERRORS)
            print(CLIENT_ERROR_PREFIX + JSON + getPostfix());
    }

    public void printSystemError(String JSON){
        if (PRINT_SYSTEM_ERRORS)
            print(SYSTEM_ERROR_PREFIX + JSON + getPostfix());
    }

    public void printInfo(String JSON){
        if(PRINT_INFO)
            print(INFO_PREFIX + JSON + getPostfix());
    }

    private String getPostfix(){
        return "\n from " + context.toString() + " LOG: " + logCNT;
    }

    private void print(String msg){
        System.out.println(msg);
        logCNT++;
    }
}
