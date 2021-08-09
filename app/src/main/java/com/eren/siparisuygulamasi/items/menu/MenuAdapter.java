package com.eren.siparisuygulamasi.items.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<Menu> {
    public MenuAdapter(Context context, ArrayList<Menu> menus){
        super(context, 0, menus);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Menu menu = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu, parent,false);
        }

        TextView menu_name_text = (TextView) convertView.findViewById(R.id.text_menu_name);
        TextView price_text = (TextView) convertView.findViewById(R.id.text_price);

        menu_name_text.setText(menu.name);
        price_text.setText(Float.toString(menu.price));

        return convertView;
    }
}
