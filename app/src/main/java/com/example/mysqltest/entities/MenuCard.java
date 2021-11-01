package com.example.mysqltest.entities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.service.voice.VoiceInteractionSession;

public class MenuCard {
    private String btnText;
    private Activity linkToActivity;
    private Drawable image;

    public MenuCard(String btnText, Drawable image, Activity linkToActivity){
        this.btnText = btnText;
        this.linkToActivity = linkToActivity;
        this.image = image;
    }

    public String getBtnText(){
        return btnText;
    }
    public Drawable getImage(){
        return image;
    }
}
