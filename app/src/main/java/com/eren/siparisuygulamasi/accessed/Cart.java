package com.eren.siparisuygulamasi.accessed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.order.CartAdapter;
import com.eren.siparisuygulamasi.items.order.Order;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ListView cartOrder_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartOrder_list = (ListView) findViewById(R.id.list_cart_orders);

        ArrayList<Order> orders = new ArrayList<Order>();
        orders.add(new Order("Menu",5, 15));
        orders.add(new Order("Menu1",2, 12.5f));
        orders.add(new Order("Menu2",3, 0.50f));
        orders.add(new Order("Menu3",1, 19.99f));
        orders.add(new Order("Menu",5, 15));
        orders.add(new Order("Menu1",2, 12.5f));
        orders.add(new Order("Menu2",3, 0.50f));
        orders.add(new Order("Menu3",1, 19.99f));
        orders.add(new Order("Menu",5, 15));
        orders.add(new Order("Menu1",2, 12.5f));
        orders.add(new Order("Menu2",3, 0.50f));
        orders.add(new Order("Menu3",1, 19.99f));
        orders.add(new Order("Menu",5, 15));
        orders.add(new Order("Menu1",2, 12.5f));
        orders.add(new Order("Menu2",3, 0.50f));
        orders.add(new Order("Menu3",1, 19.99f));
        orders.add(new Order("Menu",5, 15));
        orders.add(new Order("Menu1",2, 12.5f));
        orders.add(new Order("Menu2",3, 0.50f));
        orders.add(new Order("Menu3",1, 19.99f));
        orders.add(new Order("Menu",5, 15));
        orders.add(new Order("Menu1",2, 12.5f));
        orders.add(new Order("Menu2",3, 0.50f));
        orders.add(new Order("Menu3",1, 19.99f));
        CartAdapter adapter = new CartAdapter(this, orders);
        cartOrder_list.setAdapter(adapter);
    }
}