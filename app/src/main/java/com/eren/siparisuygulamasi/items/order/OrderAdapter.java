package com.eren.siparisuygulamasi.items.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.eren.siparisuygulamasi.R;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    public OrderAdapter(Context context, ArrayList<Order> orders){
        super(context, 0, orders);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Order order = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order, parent, false);
        }

        TextView menu_name_text = (TextView) convertView.findViewById(R.id.text_menu_name);
        TextView amount_text = (TextView) convertView.findViewById(R.id.text_amount);
        TextView subtotal_text = (TextView) convertView.findViewById(R.id.text_subtotal);

        menu_name_text.setText(order.name);
        amount_text.setText(Integer.toString(order.amount));
        subtotal_text.setText(Float.toString(order.total));

        return convertView;
    }
}
