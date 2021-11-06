package com.example.mysqltest.activities;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysqltest.R;
import com.example.mysqltest.abstraction.I_RequestabilityJSON;
import com.example.mysqltest.connection.Link;
import com.example.mysqltest.connection.Proxy;
import com.example.mysqltest.devtools.ActivityTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WebArticleActivity extends AppCompatActivity implements I_RequestabilityJSON {
    public static final String ARTICLE_ID_EX = "article_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTools.hideHeaderBar(this);
        setContentView(R.layout.web_article_activity);

        final int articleID = getIntent().getIntExtra(ARTICLE_ID_EX,-1);
        System.out.println(articleID);
        Map<String, String> data = new HashMap<>();
        data.put("id", articleID+"");
        new Proxy(this, Link.ARTICLE_CONTENT_POST, Link.ARTICLE_CONTENT_POST, data).sendJSONRequest();

    }


    @Override
    public void onResponse(JSONObject JSON, String from, String key) {
        try {
            JSONObject data = JSON.getJSONObject("data");
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            final String html = "";
            final WebView wv = (WebView) findViewById(R.id.article_content);
            wv.loadDataWithBaseURL("", data.getString("html"), mimeType, encoding, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseError(String msg, String from, String key) {

    }
}
