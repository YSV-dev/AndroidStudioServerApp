package com.example.mysqltest.devtools;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ActivityTools {
    public static void hideHeaderBar(AppCompatActivity appCompatActivity){
        appCompatActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).hide();
    }
}
