package com.example.mysqltest.entities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.service.voice.VoiceInteractionSession;

import com.example.mysqltest.abstraction.I_RequestabilityImage;
import com.example.mysqltest.connection.Proxy;

public class MenuCard {
    private String imageLink;
    private String header;
    private int id;

    public MenuCard(int id, String header, String imageLink){
        this.header = header;
        this.imageLink = imageLink;
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public String getHeader(){
        return header;
    }

    public String getLinkImage(){
        return imageLink;
    }
}
