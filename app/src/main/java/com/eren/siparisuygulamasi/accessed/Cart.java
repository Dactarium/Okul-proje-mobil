package com.eren.siparisuygulamasi.accessed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.order.CartAdapter;
import com.eren.siparisuygulamasi.items.order.Order;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ListView cartOrder_list;

    TextView order_code_text;
    TextView cart_total_text;

    ArrayList<Order> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart = (ArrayList<Order>) getIntent().getSerializableExtra("CART");

        cartOrder_list = (ListView) findViewById(R.id.list_cart_orders);

        order_code_text = (TextView) findViewById(R.id.text_order_code);
        cart_total_text = (TextView) findViewById(R.id.text_cart_total);

        CartAdapter adapter = new CartAdapter(this, cart);
        cartOrder_list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String order_code = sharedPreferences.getString("ORDER_CODE", "");

        order_code_text.setText(order_code);

        calculateCartTotal();
    }

    void calculateCartTotal(){
        float total = 0;

        for(Order cartItem: cart){
            total += cartItem.getTotal();
        }

        cart_total_text.setText(Float.toString(total));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("CART", cart);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}