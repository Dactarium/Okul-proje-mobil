package com.eren.siparisuygulamasi.items.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Category category = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category,parent,false);
        }

        TextView category_name_text = (TextView) convertView.findViewById(R.id.text_category_name);

        category_name_text.setText(category.name);

        return convertView;
    }
}
