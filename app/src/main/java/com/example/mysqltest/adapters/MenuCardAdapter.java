package com.example.mysqltest.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.example.mysqltest.R;
import com.example.mysqltest.entities.MenuCard;

import java.util.List;

public class MenuCardAdapter extends BaseAdapter {
    private List<MenuCard> menuCards;
    private Context context;

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

            ImageButton btn = (ImageButton) convertView.findViewById(R.id.button);
            //btn.setText(card.getBtnText());
            btn.setImageDrawable(card.getImage());
        }

        return (convertView);
    }
}
