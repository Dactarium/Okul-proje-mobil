package com.eren.siparisuygulamasi.accessed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.order.Order;
import com.eren.siparisuygulamasi.items.order.OrderAdapter;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {
    ListView order_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        order_list = (ListView) findViewById(R.id.list_current_orders);

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
        OrderAdapter adapter = new OrderAdapter(this, orders);
        order_list.setAdapter(adapter);
    }
}