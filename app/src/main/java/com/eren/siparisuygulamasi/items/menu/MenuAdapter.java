package com.eren.siparisuygulamasi.items.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.eren.siparisuygulamasi.R;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<Menu> {
    private MenuButtonListener menuButtonListener;
    public MenuAdapter(Context context, ArrayList<Menu> menus, MenuButtonListener listener){
        super(context, 0, menus);
        menuButtonListener = listener;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Menu menu = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu, parent,false);
        }

        TextView menu_name_text = (TextView) convertView.findViewById(R.id.text_menu_name);
        TextView price_text = (TextView) convertView.findViewById(R.id.text_price);

        ImageView menu_img = (ImageView) convertView.findViewById(R.id.img_menu);

        ImageButton add_menu_button = (ImageButton) convertView.findViewById(R.id.button_add_menu);

        menu_name_text.setText(menu.name);
        price_text.setText(Float.toString(menu.price));

        menu_img.setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.ic_restaurant));
        add_menu_button.setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.ic_add));

        add_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuButtonListener != null){
                    menuButtonListener.addToCart(menu.name, menu.price);
                }
            }
        });

        return convertView;
    }

    public void updateList(ArrayList<Menu> menus){
        this.clear();
        this.addAll(menus);
        notifyDataSetChanged();
    }
}
