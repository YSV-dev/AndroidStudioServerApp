package com.example.mysqltest.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysqltest.R;
import com.example.mysqltest.abstraction.I_RequestabilityImage;
import com.example.mysqltest.activities.WebArticleActivity;
import com.example.mysqltest.connection.Proxy;
import com.example.mysqltest.entities.MenuCard;

import java.util.ArrayList;
import java.util.List;

public class MenuCardAdapter extends BaseAdapter implements I_RequestabilityImage {
    private final List<MenuCard> menuCards;
    private final List<View> views = new ArrayList<>();
    private final Context context;

    public MenuCardAdapter(Context context, List<MenuCard> menuCards){
        this.menuCards = menuCards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return menuCards.size();
    }

    @Override
    public MenuCard getItem(int position) {
        return menuCards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        MenuCard card = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_cell, parent, false);
            TextView header = (TextView) convertView.findViewById(R.id.header_title);
            header.setText(card.getHeader());
            views.add(convertView);
            new Proxy(context, this, card.getLinkImage(), position + "", null).sendImageRequest();
        }
        return (convertView);
    }

    @Override
    public void onResponse(Bitmap bitmap, String from, String key) {
        int id = Integer.parseInt(key);
        Drawable image = new BitmapDrawable(context.getResources(), bitmap);
        View convertView = views.get(id);
        ((ImageView)convertView.findViewById(R.id.button)).setImageDrawable(image);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebArticleActivity.class);
                intent.putExtra(WebArticleActivity.ARTICLE_ID_EX, menuCards.get(id).getID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onResponseError(String msg, String from, String key) {

    }
}
