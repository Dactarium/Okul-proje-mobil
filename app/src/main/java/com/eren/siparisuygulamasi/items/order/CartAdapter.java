package com.eren.siparisuygulamasi.items.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<Order> {
    private CartButtonListener cartButtonListener;
    public CartAdapter(Context context, ArrayList<Order> orders){
        super(context, 0, orders);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Order order = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_order, parent, false);
        }

        TextView menu_name_text = (TextView) convertView.findViewById(R.id.text_menu_name);
        Button amount_button = (Button) convertView.findViewById(R.id.button_amount);
        TextView subtotal_text = (TextView) convertView.findViewById(R.id.text_subtotal);
        ImageButton remove_button = (ImageButton) convertView.findViewById(R.id.button_remove);

        menu_name_text.setText(order.name);
        amount_button.setText(Integer.toString(order.amount));
        subtotal_text.setText(Float.toString(order.total));

        amount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartButtonListener != null){
                    cartButtonListener.changeAmount(position, amount_button);
                }
            }
        });

        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartButtonListener != null){
                    cartButtonListener.removeFromCart(position);
                }
            }
        });

        return convertView;
    }

    public void setCartButtonListener(CartButtonListener cartButtonListener) {
        this.cartButtonListener = cartButtonListener;
    }

    public void updateList(ArrayList<Order> list){
        clear();
        addAll(list);
        notifyDataSetChanged();
    }
}
