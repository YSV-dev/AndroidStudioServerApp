package com.example.mysqltest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mysqltest.R;
import com.example.mysqltest.abstraction.I_RequestabilityImage;
import com.example.mysqltest.abstraction.I_RequestabilityJSON;
import com.example.mysqltest.adapters.MenuCardAdapter;
import com.example.mysqltest.connection.Link;
import com.example.mysqltest.connection.Proxy;
import com.example.mysqltest.devtools.ActivityTools;
import com.example.mysqltest.devtools.Logger;
import com.example.mysqltest.entities.ClientProfile;
import com.example.mysqltest.entities.MenuCard;
import com.example.mysqltest.entities.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainMenuActivity extends AppCompatActivity implements I_RequestabilityJSON, I_RequestabilityImage {

    private TextView profile_login;
    private GridView mainMenu;
    private Context context;
    private Profile clientProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTools.hideHeaderBar(this);
        setContentView(R.layout.main_menu_activity);
        context = this;
        clientProfile = ClientProfile.getProfile();

        setElements();
        addElements();
        setData();
        setEvents();
    }

    private void addElements(){

        new Proxy(this, Link.NEWS_POST, Link.NEWS_POST, null).sendJSONRequest();

        List<MenuCard> menuCards = new ArrayList<>();
//        menuCards.add(new MenuCard("test1", this));
//        menuCards.add(new MenuCard("test2", this));
//        menuCards.add(new MenuCard("test3", this));
//        menuCards.add(new MenuCard("test4", this));
        MenuCardAdapter adapter = new MenuCardAdapter(context, menuCards);

        mainMenu.setAdapter(adapter);
    }

    private void setData(){
        profile_login.setText(clientProfile.getLogin());
    }

    private void setElements(){
        mainMenu = findViewById(R.id.main_menu_content);
        profile_login = findViewById(R.id.profile_login_label);
    }

    private void setEvents(){

    }

    @Override
    public void onResponse(Bitmap bitmap, String from, String key) {

    }

    @Override
    public void onResponse(JSONObject JSON, String from, String key) {
        if(key.equals(Link.NEWS_POST)){
            try {
                JSONObject data = JSON.getJSONObject("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseError(String msg, String from, String key) {
        new Logger(this).printSystemError(msg);
    }
}