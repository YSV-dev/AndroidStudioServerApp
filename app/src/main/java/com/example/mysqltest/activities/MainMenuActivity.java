package com.example.mysqltest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.mysqltest.R;
import com.example.mysqltest.abstraction.I_RequestabilityImage;
import com.example.mysqltest.abstraction.I_RequestabilityJSON;
import com.example.mysqltest.adapters.MenuCardAdapter;
import com.example.mysqltest.connection.Link;
import com.example.mysqltest.connection.Proxy;
import com.example.mysqltest.devtools.ActivityTools;
import com.example.mysqltest.devtools.Logger;
import com.example.mysqltest.devtools.LoggerErrors;
import com.example.mysqltest.entities.ClientProfile;
import com.example.mysqltest.entities.MenuCard;
import com.example.mysqltest.entities.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements I_RequestabilityJSON, I_RequestabilityImage {

    private TextView profile_login;
    private GridView mainMenu;
    private Context context;
    private Profile clientProfile;

    private final List<MenuCard> menuCards = new ArrayList<>();
    private Logger log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTools.hideHeaderBar(this);
        setContentView(R.layout.main_menu_activity);

        log = new Logger(this);

        context = this;
        clientProfile = ClientProfile.getProfile();

        setElements();
        new Proxy(this, Link.NEWS_POST, Link.NEWS_POST, null).sendJSONRequest();
        setData();
        setEvents();
    }

    private void addElements(){

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
                JSONArray data = JSON.getJSONArray("data");
                for(int cellID = 0; cellID != data.length(); cellID++) {
                    JSONObject cell = data.getJSONObject(cellID);
                    int id = cell.getInt("id");
                    String header = cell.getString("header");
                    String imageLink = cell.getString("image_link");
                    menuCards.add(new MenuCard(id, header, imageLink));
                }
            } catch (JSONException e) {
                log.printSystemError(LoggerErrors.JSON_PARSE_ERROR);
                e.printStackTrace();
            }
            addElements();
        }
    }

    @Override
    public void onResponseError(String msg, String from, String key) {
        log.printSystemError(msg);
    }
}